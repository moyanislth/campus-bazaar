package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Product {
    private Long id;

    private String name;

    private String category;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Integer stock;

    private Long merchantId;

    private Byte status;

    private Boolean isBargain;

    private String condition;

    private String description;

}