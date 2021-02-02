package com.hzcong.springboot.service.Impl;

import com.hzcong.config.sdk.WXPayConfig;
import com.hzcong.springboot.service.IWXPayDomain;

public class WXPayDomainSimpleImpl implements IWXPayDomain {


    public static WXPayDomainSimpleImpl getInstance(){
        return new WXPayDomainSimpleImpl();
    }


    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo("api.mch.weixin.qq.com",true);
    }
}
