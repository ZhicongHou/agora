package com.hzcong.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {
    private String id;
    private String name;
    private String sign;
    private String description;





//    private Set<UserEntity> userSet;
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name="user_role",
//            joinColumns=@JoinColumn(name="role_id",referencedColumnName = "id"),
//            inverseJoinColumns=@JoinColumn(name="user_id",referencedColumnName = "id"))
//    public Set<UserEntity> getUserSet() {
//        return userSet;
//    }
//
//    public void setUserSet(Set<UserEntity> userSet) {
//        this.userSet = userSet;
//    }


    //    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name = "role_permission",joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"))
//    private Set<PermissionEntity> permissionSet;

    @Id
    @Column(name = "id", columnDefinition = "char(32)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sign", columnDefinition = "char(32)")
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sign != null ? !sign.equals(that.sign) : that.sign != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
