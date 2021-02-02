package com.hzcong.data.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bill")
public class BillEntity {
    private String billId;
    private String outBizNo;
    private String userId;
    private String payType;
    private double price;
    private Timestamp finishTime;
    private String secId;
    private String state;
    private String detail;

    @Id
    @Column(name = "bill_id", columnDefinition = "char(32)")
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    @Basic
    @Column(name = "out_biz_no")
    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    @Basic
    @Column(name = "user_id", columnDefinition = "char(32)")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "pay_type", columnDefinition = "char(32)")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "finish_time")
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "sec_id", columnDefinition = "char(32)")
    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillEntity that = (BillEntity) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (billId != null ? !billId.equals(that.billId) : that.billId != null) return false;
        if (outBizNo != null ? !outBizNo.equals(that.outBizNo) : that.outBizNo != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (finishTime != null ? !finishTime.equals(that.finishTime) : that.finishTime != null) return false;
        if (secId != null ? !secId.equals(that.secId) : that.secId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (detail != null ? !detail.equals(that.detail) : that.detail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billId != null ? billId.hashCode() : 0;
        result = 31 * result + (outBizNo != null ? outBizNo.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (finishTime != null ? finishTime.hashCode() : 0);
        result = 31 * result + (secId != null ? secId.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }
}
