package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserCoupon {
    private Long id;

    private Long userId;

    private Long couponId;

    private Byte status;

    private Date usedTime;

    private String orderId;

    private Date createdAt;

}