package com.hzcong.springboot.repository;

import com.hzcong.data.entities.BillEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<BillEntity,String> {

    BillEntity getBillEntityByOutBizNo(String outBizNo);

    @Modifying
    @Query(nativeQuery = true,value = "update bill set state = :state where out_biz_no = :outBizNo")
    int updateBillByoutBizNo(@Param("outBizNo") String outBizNo,@Param("state")String state);

}
