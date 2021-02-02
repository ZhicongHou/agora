package com.hzcong.springboot.service.Impl;

import com.hzcong.config.sdk.WXPay;
import com.hzcong.config.sdk.WXPayUtil;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WXService {
    private static Logger logger = LoggerFactory.getLogger(WXService.class);
    private WXPay wxpay;
    private WXPayConfigImpl config;
    private static WXService INSTANCE;


    private WXService() throws Exception {
            config = WXPayConfigImpl.getInstance();
            wxpay = new WXPay(config);
    }

    public static WXService getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXService();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 下单
     *
     * @param out_trade_no
     * @param body
     * @param money
     * @param applyNo
     * @return
     */
    public Map<String, String> doUnifiedOrder(String out_trade_no, String body, Double money, String applyNo,String attach) {
        String amt = String.valueOf(money * 100);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", body);
        data.put("out_trade_no", out_trade_no);
        data.put("device_info", "web");
        data.put("fee_type", "CNY");
        data.put("total_fee", amt.substring(0, amt.lastIndexOf(".")));
        data.put("spbill_create_ip", config.getSpbillCreateIp());
        data.put("notify_url", config.getNotifUrl());
        data.put("trade_type", config.getTradeType());
        data.put("product_id", applyNo);
        data.put("attach", attach);
        System.out.println(String.valueOf(money * 100));
        // data.put("time_expire", "20170112104120");
        try {
            Map<String, String> r = wxpay.unifiedOrder(data);
            logger.info("返回的参数是" + r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return null;
        }
    }

    /**
     * 退款 已测试
     */
    public void doRefund(String out_trade_no, String total_fee) {
        logger.info("退款时的订单号为：" + out_trade_no + "退款时的金额为:" + total_fee);
        String amt = String.valueOf(Double.parseDouble(total_fee) * 100);
        logger.info("修正后的金额为：" + amt);
        logger.info("最终的金额为：" + amt.substring(0, amt.lastIndexOf(".")));
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", out_trade_no);
        data.put("out_refund_no", out_trade_no);
        data.put("total_fee", amt.substring(0, amt.lastIndexOf(".")));
        data.put("refund_fee", amt.substring(0, amt.lastIndexOf(".")));
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());

        try {
            Map<String, String> r = wxpay.refund(data);
            logger.info("退款操作返回的参数为" + r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        /**
         * 微信验签接口
         * @param strXML
         * @return
         * @throws DocumentException
         */
        public boolean checkSign(String  strXML) {
//            SortedMap<String, String> smap = new TreeMap<String, String>();
//            Document doc = DocumentHelper.parseText(strXML);
//            Element root = doc.getRootElement();
//            for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
//                Element e = (Element) iterator.next();
//                smap.put(e.getName(), e.getText());
//            }
//            return isWechatSign(smap,config.getKey());
            try {
                return WXPayUtil.isSignatureValid(strXML,config.getKey());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }





//        private boolean isWechatSign(SortedMap<String, String> smap, String apiKey) {
//            StringBuffer sb = new StringBuffer();
//            Set<Map.Entry<String, String>> es = smap.entrySet();
//            Iterator<Map.Entry<String, String>> it = es.iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, String> entry =  it.next();
//                String k = (String) entry.getKey();
//                String v = (String) entry.getValue();
//                if (!"sign".equals(k) && null != v && !"".equals(v) && !"key".equals(k)) {
//                    sb.append(k + "=" + v + "&");
//                }
//            }
//            sb.append("key=" + apiKey);
//            /** 验证的签名 */
////            String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
//            String sign = "";
//            try {
//                sign = WXPayUtil.MD5(sb.toString()).toUpperCase();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            /** 微信端返回的合法签名 */
//            String validSign = ((String) smap.get("sign")).toUpperCase();
//            return validSign.equals(sign);
//    }
}
