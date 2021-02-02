package com.hzcong.springboot.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hzcong.config.AlipayConfig;
import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.BillEntity;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.service.BillService;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@Controller
public class PayController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    //购买课程所需要的参数：学生ID、课程ID、订单号ID（自动生成）
    @RequestMapping("/alipay")
    public void pay(HttpServletRequest request, HttpServletResponse response,
                    @RequestParam String secId, HttpSession session) throws IOException, AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        SectionEntity section = sectionService.getSectionById(secId);
        UserEntity user = (UserEntity)session.getAttribute("user");
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = Util.createTimeChars();
//        System.out.println(out_trade_no);
//        System.out.println("订单号长度："+out_trade_no.length());
        //付款金额，必填
        String total_amount = String.valueOf(section.getPrice());
        //订单名称，必填。……这里就是课程
        String subject = section.getTitle();
        //商品描述，可空
//        String body = request.getParameter("WIDbody");
        String passback_params = section.getSecId()+","+user.getId();


        BillEntity bill = new BillEntity();
        bill.setBillId(Util.createRandom32Chars());
        bill.setOutBizNo(out_trade_no);
        bill.setPayType(SystemConstants.ALIPAY);
        bill.setUserId(user.getId());
        bill.setSecId(section.getSecId());
        bill.setFinishTime(new Timestamp(System.currentTimeMillis()));
        bill.setPrice(section.getPrice());
        bill.setState("0");
        bill.setDetail("");
        BillEntity temp = billService.saveBill(bill);

        if (temp == null) {
            out.println("<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                    "<title>支付结果</title>\n" +
                    "</head><body>账单错误，保存记录失败</body>\n" +
                    "</html>");
            return;
        }

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"passback_params\":\""+ passback_params +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //输出
        out.println("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<title>进入支付宝</title>\n" +
                "</head><body>");
        out.println(result);
        out.println("</body></html>");
        out.close();


        //用于购买成功后跳转
        session.setAttribute("boughtSecId",secId);

    }
}
