package com.hzcong.data.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tea_authentication")
public class TeaAuthenticationEntity  implements Serializable {
    private String id;
    private String phoneNumber;
    private String userId;
    private String userName;
    private boolean authorized;
    private String resume;
    private String alipayAccount;
    private String IDNumber;



    @Id
    @Column(name = "id", columnDefinition = "char(32)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    @Column(name = "resume")
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Basic
    @Column(name = "alipay_account")
    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    @Basic
    @Column(name = "ID_number")
    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeaAuthenticationEntity that = (TeaAuthenticationEntity) o;

        if (phoneNumber != that.phoneNumber) return false;
        if (authorized != that.authorized) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (resume != null ? !resume.equals(that.resume) : that.resume != null) return false;
        if (alipayAccount != null ? !alipayAccount.equals(that.alipayAccount) : that.alipayAccount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (resume != null ? resume.hashCode() : 0);
        result = 31 * result + (alipayAccount != null ? alipayAccount.hashCode() : 0);
        return result;
    }
}
