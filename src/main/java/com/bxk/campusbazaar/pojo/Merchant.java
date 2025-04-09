package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Merchant {
    private Long userId;

    private String licenseImg;

    private String idCardImg;

    private Byte level;

    private Integer totalSales;

    private BigDecimal walletBalance;

}