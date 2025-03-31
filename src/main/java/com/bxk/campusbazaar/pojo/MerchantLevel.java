package com.bxk.campusbazaar.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantLevel {
    private Byte level;

    private BigDecimal feeRate;

    private String description;

}