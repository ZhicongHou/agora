package com.hzcong.data.entities;

import com.hzcong.util.Util;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity  implements Serializable {
    private String id;
    private String email;
    private String userName;
    private String password;
    private String realName;
    private boolean actived;
    private String sex;
    private Timestamp createTime;
    private String uid;
    private boolean isChangingPw;
    private String changingPwSign;
    private boolean prohibited;

    public UserEntity(){}

    public UserEntity(String email, String userName, String password, String realName, boolean actived, String sex) {
        this.id = Util.createRandom32Chars();
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.actived = actived;
        this.sex = sex;
        this.createTime = new Timestamp(new Date().getTime());
        this.uid = Util.createRandom32Chars();
    }

    //1、关系维护端，负责多对多关系的绑定和解除
    //2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(user)
    //3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(role)
    //4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为user_role
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即role_id
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表


    private Set<RoleEntity> roleSet;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    public Set<RoleEntity> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<RoleEntity> roleSet) {
        this.roleSet = roleSet;
    }


    private Set<MessageEntity> messages;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_message",
            joinColumns={ @JoinColumn(name="userid",referencedColumnName = "id")},
            inverseJoinColumns={@JoinColumn(name="mid",referencedColumnName = "id")})
    public Set<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

    @Id
    @Column(name = "id", columnDefinition = "char(32)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "actived")
    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "uid", columnDefinition = "char(32)")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "is_changing_pw")
    public boolean isChangingPw() {
        return isChangingPw;
    }

    public void setChangingPw(boolean ChangingPw) {
        this.isChangingPw = ChangingPw;
    }

    @Basic
    @Column(name = "changing_pw_sign")
    public String getChangingPwSign() {
        return changingPwSign;
    }

    public void setChangingPwSign(String changingPwSign) {
        this.changingPwSign = changingPwSign;
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

        UserEntity that = (UserEntity) o;

        if (isChangingPw != that.isChangingPw) return false;
        if (prohibited != that.prohibited) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) return false;
        if (actived != that.actived) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (changingPwSign != null ? !changingPwSign.equals(that.changingPwSign) : that.changingPwSign != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (changingPwSign != null ? changingPwSign.hashCode() : 0);
        return result;
    }
}
