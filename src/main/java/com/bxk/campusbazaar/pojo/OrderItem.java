package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItem {
    private Long id;

    private String orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal price;

}