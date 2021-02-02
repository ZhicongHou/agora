package com.hzcong.springboot.service.Impl;

import com.hzcong.config.sdk.WXPayConfig;
import com.hzcong.springboot.service.IWXPayDomain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig {
    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception{
        String certPath = this.getClass().getClassLoader().getResource("./certificate/apiclient_cert.p12").getPath();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return "wx8d89ad392e0eccef";
    }

    public String getMchID() {
        return "1517293291";
    }

    public String getKey() {
        return "Scaueducn20181113eoirunmncIUNVY1";
    }

    public String getNotifUrl() {
        return "https://noi.uutime.cn/pay_notify";
    }

    public String getTradeType() {
        return "NATIVE";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }


      public IWXPayDomain getWXPayDomain() {
          return WXPayDomainSimpleImpl.getInstance();
      }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    public int getReportWorkerNum() {
        return 1;
    }

    public int getReportBatchSize() {
        return 2;
    }

    public String getSpbillCreateIp() {
        return "192.168.1.1";
    }
}
