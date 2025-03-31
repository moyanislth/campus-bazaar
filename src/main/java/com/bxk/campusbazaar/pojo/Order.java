package com.bxk.campusbazaar.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private String id;

    private Long userId;

    private BigDecimal totalAmount;

    private Byte status;

    private Date createdAt;

    private Date completedAt;

}