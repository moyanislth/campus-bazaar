package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

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

    private Instant createdAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", gender=" + gender +
                ", bankAccount='" + bankAccount + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}