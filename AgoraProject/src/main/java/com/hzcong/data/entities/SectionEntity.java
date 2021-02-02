package com.hzcong.data.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "section")
public class SectionEntity {
    private String secId;
    private String courId;
    private String teaId;
    private Date startDate;
    private String classTime;
    private double price;
    private boolean authorized;
    private int upperLimit;
    private int curAmount;
    private boolean purchased;
    private String roomNumber;
    private String title;
    private int frequency;
    private String teaName;
    private int attended;
    private String judgement;
    private boolean paid;
    private String introduction;
    private Double proportion;
    private double actualAmount;
    private boolean inClass;
    private boolean prohibited;

    @Id
    @Column(name = "sec_id", columnDefinition = "char(32)")
    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    @Basic
    @Column(name = "cour_id", columnDefinition = "char(32)")
    public String getCourId() {
        return courId;
    }

    public void setCourId(String courId) {
        this.courId = courId;
    }

    @Basic
    @Column(name = "tea_id", columnDefinition = "char(32)")
    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "class_time")
    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
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
    @Column(name = "authorized")
    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @Basic
    @Column(name = "upper_limit")
    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Basic
    @Column(name = "cur_amount")
    public int getCurAmount() {
        return curAmount;
    }

    public void setCurAmount(int curAmount) {
        this.curAmount = curAmount;
    }

    @Basic
    @Column(name = "purchased")
    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Basic
    @Column(name = "room_number", columnDefinition = "char(32)")
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "frequency")
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Basic
    @Column(name = "tea_name")
    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    @Basic
    @Column(name = "attended")
    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    @Basic
    @Column(name = "judgement")
    public String getJudgement() {
        return judgement;
    }

    public void setJudgement(String judgement) {
        this.judgement = judgement;
    }

    @Basic
    @Column(name = "paid")
    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Basic
    @Column(name = "introduction",columnDefinition = "text")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "proportion")
    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    @Basic
    @Column(name = "actual_amount")
    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    @Basic
    @Column(name = "in_class")
    public boolean isInClass() {
        return inClass;
    }

    public void setInClass(boolean inClass) {
        this.inClass = inClass;
    }

    @Basic
    @Column(name = "prohibited")
    public boolean isProhibited() {
        return prohibited;
    }

    public void setProhibited(boolean prohibited) {
        this.prohibited = prohibited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionEntity that = (SectionEntity) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (authorized != that.authorized) return false;
        if (upperLimit != that.upperLimit) return false;
        if (curAmount != that.curAmount) return false;
        if (purchased != that.purchased) return false;
        if (frequency != that.frequency) return false;
        if (attended != that.attended) return false;
        if (paid != that.paid) return false;
        if (Double.compare(that.actualAmount, actualAmount) != 0) return false;
        if (inClass != that.inClass) return false;
        if (prohibited != that.prohibited) return false;
        if (secId != null ? !secId.equals(that.secId) : that.secId != null) return false;
        if (courId != null ? !courId.equals(that.courId) : that.courId != null) return false;
        if (teaId != null ? !teaId.equals(that.teaId) : that.teaId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (classTime != null ? !classTime.equals(that.classTime) : that.classTime != null) return false;
        if (roomNumber != null ? !roomNumber.equals(that.roomNumber) : that.roomNumber != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (teaName != null ? !teaName.equals(that.teaName) : that.teaName != null) return false;
        if (judgement != null ? !judgement.equals(that.judgement) : that.judgement != null) return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (proportion != null ? !proportion.equals(that.proportion) : that.proportion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = secId != null ? secId.hashCode() : 0;
        result = 31 * result + (courId != null ? courId.hashCode() : 0);
        result = 31 * result + (teaId != null ? teaId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (classTime != null ? classTime.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + upperLimit;
        result = 31 * result + curAmount;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + frequency;
        result = 31 * result + (teaName != null ? teaName.hashCode() : 0);
        result = 31 * result + attended;
        result = 31 * result + (judgement != null ? judgement.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (proportion != null ? proportion.hashCode() : 0);
        temp = Double.doubleToLongBits(actualAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
