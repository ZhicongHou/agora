package com.hzcong.springboot.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hzcong.config.AlipayConfig;
import com.hzcong.data.entities.BillEntity;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.data.entities.TaskEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.service.BillService;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.springboot.service.TaskService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class PayResponse {

    @Autowired
    private TaskService taskService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    @RequestMapping("/notify_url")
    public void notify_url(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        for (Map.Entry<String,String> param: params.entrySet()) {
            System.out.println(param.getKey()+" "+param.getValue());
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号
            String trade_no = request.getParameter("trade_no");
            //订单名称
            String subject = request.getParameter("subject");

            String selfParams[] = request.getParameter("passback_params").split(",");
            String secId = selfParams[0];
            String stuId = selfParams[1];

            //交易状态
            String trade_status = request.getParameter("trade_status");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                try {
                    BillEntity bill = billService.getBillbyOutTradeNO(out_trade_no);
                    billService.updateStatebyOutTradeNo(out_trade_no, "1");
                    //写入成功
                    UserEntity user = userService.getUserByUserId(bill.getUserId());

                    SectionEntity section = sectionService.getSectionById(bill.getSecId());
                    TaskEntity taskEntity = new TaskEntity();
                    taskEntity.setTaskId(Util.createRandom32Chars());
                    taskEntity.setCourId(section.getCourId());
                    taskEntity.setTeaId(section.getTeaId());
                    taskEntity.setStuId(bill.getUserId());
                    System.out.println(bill.getUserId());
                    taskEntity.setRoomNumber(section.getRoomNumber());
                    taskEntity.setSecId(section.getSecId());
                    taskEntity.setClasstime(section.getClassTime());
                    taskEntity.setTeaName(section.getTeaName());
                    taskEntity.setStartDate(section.getStartDate());
                    taskEntity.setTitle(section.getTitle());
                    taskEntity.setStuName(user.getUserName());
                    TaskEntity temp2 = taskService.saveTask(taskEntity);
                    if(temp2 == null){
                        out.println("添加task对象错误");
                    }else{
                        sectionService.updateCurAmount(bill.getSecId());
                        SectionEntity sectionEntity = sectionService.getSectionById(bill.getSecId());
                        sectionService.updateActualAmountBySecId(bill.getSecId(),sectionEntity.getCurAmount()*sectionEntity.getPrice()*sectionEntity.getProportion());
                    }
                }catch (RuntimeException e) {
                    throw e;
                }
            }
            out.println("success");
        }else {//验证失败
            out.println("fail");
            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        out.close();
    }

    @RequestMapping("/return_url")
    public void return_url(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String boughtSecId = (String)session.getAttribute("boughtSecId");
        session.removeAttribute("boughtSecId");
        response.sendRedirect("/getSection?secId="+boughtSecId);
    }
}
