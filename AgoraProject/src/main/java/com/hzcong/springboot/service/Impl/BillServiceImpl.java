package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.BillEntity;
import com.hzcong.springboot.repository.BillRepository;
import com.hzcong.springboot.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Transactional
    public BillEntity saveBill(BillEntity bill){
        return billRepository.save(bill);
    }

    @Transactional
    public BillEntity getBillbyOutTradeNO(String out_trade_no) {
        return billRepository.getBillEntityByOutBizNo(out_trade_no);
    }

    @Transactional
    public boolean updateStatebyOutTradeNo(String out_trade_no, String state) {
        return billRepository.updateBillByoutBizNo(out_trade_no, state)!=0;
    }

}
