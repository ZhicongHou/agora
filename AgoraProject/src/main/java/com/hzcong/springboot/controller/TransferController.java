package com.hzcong.springboot.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.hzcong.config.AlipayConfig;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.BillService;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.springboot.service.TaskService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class TransferController {

    @Autowired
    private BillService billService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    private static String remark = "老师工资结算";

    @ResponseBody
    @RequestMapping("/doTransfer")
    private Message doTransfer(@RequestParam("secId")String secId, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {//BillEntity bill, @RequestParam String teacherName,
//        if(userService.getUserByUserName(teacherName)==null){
//            return  new ModelAndView("welcomeAdmin");
//        }
//        bill.setPayeeType("ALIPAY_LOGONID");
//        bill.setPayeeAccount(userService.getUserByUserName(teacherName).getEmail());
//        bill.setPayerShowName("admin");
//        bill.setRemark(remark);
//        System.out.println(bill);

        SectionEntity section = sectionService.getSectionById(secId);
        double temp = section.getPrice()*section.getCurAmount()*section.getProportion()/100;
        BigDecimal bg = new BigDecimal(temp);
        double amount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + Util.createTimeChars() + "\"," +
                "\"payee_type\":" +"\"ALIPAY_LOGONID\"" + "," +
                "\"payee_account\":" + "\"17665156970\"" + "," +
//                "\"amount\":" + "\"0.15\"" + "," +
                "\"amount\":\"" + amount + "\"," +
//                "\"payer_show_name\":" + "\"hzc\"" + "," +
//                "\"payee_real_name\":" + "\"hzc\"" + "," +
                "\"remark\":\"" + section.getTitle() +"\"" +
                "}");

        AlipayFundTransToaccountTransferResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        assert response != null;
        System.out.println("转账返回码:"+response.getCode()+", 转账返回信息："+response.getMsg()+" "+response.getSubCode());

        if (response.isSuccess()) {
            sectionService.updateActualAmountBySecId(secId, amount);
            sectionService.updatePaidBySecId(secId, true);
            return new Message("0","成功支付");
//            billService.addBill(bill)a;
        } else {
            System.out.println("调用失败");
//            queryBill(bill.getOutBizNo());
            return new Message("1","支付失败！");
        }
    }

//    @ResponseBody
//    @RequestMapping(value =  "/queryBill",method = RequestMethod.GET)
//    public String queryBill(String outBizNo) {
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
//        request.setBizContent("{" +
//                "\"out_biz_no\":"+outBizNo+"," +
//                "\"order_id\":\"\"" +
//                "  }");
//        AlipayFundTransOrderQueryResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        if (response.isSuccess()) {
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//       return  null;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getMoney", method = RequestMethod.GET)
//    public ConcurrentMap<String, Double> getMoney(){
//        Iterable<SectionEntity> list = sectionService.getAllSections();
//        ConcurrentMap<String, Double> map = new ConcurrentHashMap<>();
//        for (SectionEntity section : list) {
//            if(map.get(section.getTeaId())==null){
//                map.put(section.getTeaId(),section.getPrice());
//            }else{
//                double money = map.remove(section.getPrice());
//                map.put(section.getTeaId(),section.getPrice()+money);
//            }
//        }
//        return map;
//    }

}
