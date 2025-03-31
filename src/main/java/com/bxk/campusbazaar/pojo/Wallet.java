package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Wallet {
    private Long userId;

    private BigDecimal balance;

    private Integer integral;

}