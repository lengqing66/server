package com.embraiz.entity.vo;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Data
@Immutable
@Table(name = "v_user_role")
public class VoUserRole implements Serializable {
    @Id
    @Column(name = "userRoleId")
    private Integer userRoleId;

    @Column(name = "userId")
    private BigInteger userId;

    @Column(name = "roleId")
    private BigInteger roleId;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "userName")
    private String userName;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobilePhone")
    private String mobilePhone;

    @Column(name = "directPhone")
    private String directPhone;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "title")
    private String title;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "isFirstLogin")
    private Integer isFirstLogin;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isRemove")
    private Integer isRemove;

    @Column(name = "activeDate")
    private String activeDate;
}
