package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private Long id;

    private String username;

    private String password;

    private Byte role;

    private String phone;

    private Byte status;

    private String email;

    private String city;

    private Byte gender;

    private String bankAccount;

    private Date createdAt;

}