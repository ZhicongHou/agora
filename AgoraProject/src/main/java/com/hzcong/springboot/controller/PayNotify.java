package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.config.sdk.WXPayUtil;
import com.hzcong.data.entities.BillEntity;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.data.entities.TaskEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.BillService;
import com.hzcong.springboot.service.Impl.WXService;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.springboot.service.TaskService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Controller
public class PayNotify {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private BillService billService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private WXService wxService;

    @RequestMapping(value = "/pay_notify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader reader = request.getReader();
        String line;
        StringBuffer inputString = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        String xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("微信回调信息:" + xmlString);
        //微信jdk提供的xml转Map
        Map<String, String> requestParams = WXPayUtil.xmlToMap(xmlString);
        //返回结果
        String returnCode = requestParams.get("return_code");
        //业务返回结果
        String businessResultCode = requestParams.get("result_code");
        response.setContentType("text/xml");
        //订单号
        String outTradeNo = requestParams.get("out_trade_no");
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(businessResultCode) && wxService.checkSign(xmlString)) {
            //返回金额
//            String totalFee = requestParams.get("total_fee");
            //调用业务接口，返回成功失败

//            String[] attach = requestParams.get("attach").split(",");
//            String userId = attach[0];
//            String secId = attach[1];
            BillEntity bill = billService.getBillbyOutTradeNO(outTradeNo);
//            if(bill == null){
//                response.getWriter().write("<xml>\n"
//                        + "  <return_code><![CDATA[FAIL]]></return_code>\n"
//                        + "  <return_msg><![CDATA[回写bill数据失败]]></return_msg>\n"
//                        + "</xml>");
//            }
            billService.updateStatebyOutTradeNo(outTradeNo, "1");
            //写入成功
            SectionEntity section = sectionService.getSectionById(bill.getSecId());
            UserEntity user = userService.getUserByUserId(bill.getUserId());

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
            if (temp2 == null) {
                response.getWriter().write("<xml>\n"
                        + "  <return_code><![CDATA[FAIL]]></return_code>\n"
                        + "  <return_msg><![CDATA[回写task数据失败]]></return_msg>\n"
                        + "</xml>");
            }else {
                sectionService.updateCurAmount(bill.getSecId());
                SectionEntity sectionEntity = sectionService.getSectionById(bill.getSecId());
                sectionService.updateActualAmountBySecId(bill.getSecId(),sectionEntity.getCurAmount()*sectionEntity.getPrice()*sectionEntity.getProportion());
                response.getWriter().write(
                        "<xml>\n"
                                + "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
                                + "  <return_msg><![CDATA[OK]]></return_msg>\n"
                                + "</xml>");
            }
            //写入失败
        } else {
            System.out.println("微信回调支付失败:" + xmlString);
            billService.updateStatebyOutTradeNo(outTradeNo, "2");
            // 返回错误
            response.getWriter().write("<xml>\n"
                    + "  <return_code><![CDATA[FAIL]]></return_code>\n"
                    + "  <return_msg><![CDATA[下单失败]]></return_msg>\n"
                    + "</xml>");
        }
    }

    @RequestMapping(value = "/checkPaid")
    public void checkPaid(HttpServletRequest request, HttpServletResponse response) {
        String out_trade_no = request.getParameter("out_trade_no");
        BillEntity bill = billService.getBillbyOutTradeNO(out_trade_no);
        try {
            response.getWriter().write(bill.getState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createCode", method = RequestMethod.POST)
    public Message createCode(String secId, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
//        ModelAndView modelAndView = new ModelAndView("qrcode");
        SectionEntity section = sectionService.getSectionById(secId);


        String out_trade_no = Util.createTimeChars();
        BillEntity bill = new BillEntity();
        bill.setBillId(Util.createRandom32Chars());
        bill.setOutBizNo(out_trade_no);
        bill.setPayType(SystemConstants.WXPAY);
        bill.setUserId(user.getId());
        bill.setSecId(secId);
        bill.setFinishTime(new Timestamp(System.currentTimeMillis()));
        bill.setPrice(section.getPrice());
        bill.setState("0");
        bill.setDetail("");
        BillEntity temp = billService.saveBill(bill);

        if (section == null || temp == null) {
            return new Message("1","创建二维码失败！");
        }

        Map<String, String> map = null;
        String attach = user.getId() + "," + section.getSecId();
        try {
            map = WXService.getInstance().doUnifiedOrder(out_trade_no, section.getTitle(), section.getPrice(), secId, attach);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String retContent = map.get("code_url") + "," + out_trade_no;
        return new Message("0",retContent);
    }
}
