package com.bxk.campusbazaar.pojo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class Merchant {
    private Long userId;

    private String licenseImg;

    private String idCardImg;

    private Byte level;

    private Integer totalSales;

    private BigDecimal walletBalance;

}