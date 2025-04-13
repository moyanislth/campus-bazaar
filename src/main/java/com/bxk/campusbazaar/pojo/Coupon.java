package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class Coupon {
    private Long id;

    private String name;

    private Byte type;

    private BigDecimal value;

    private BigDecimal minAmount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer totalCount;

    private Integer remainingCount;

    private Long createdBy;

    private LocalDateTime createdAt;

}