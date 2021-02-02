package com.hzcong.springboot.service;

import com.hzcong.data.entities.BillEntity;

public interface BillService {

    BillEntity saveBill(BillEntity bill);

    BillEntity getBillbyOutTradeNO(String out_trade_no);

    boolean updateStatebyOutTradeNo(String out_trade_no,String state);
}
