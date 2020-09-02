package com.embraiz.entity.server;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Data
@Table(name = "obj_user")
public class ObjUser implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "password_des")
    private String passwordDes;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "direct_phone")
    private String directPhone;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "title")
    private String title;

    @Column(name = "position")
    private String position;

    @Column(name = "first_login")
    private Integer firstLogin;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_remove")
    private Integer isRemove;

    @Column(name = "active_date")
    private String activeDate;
}
