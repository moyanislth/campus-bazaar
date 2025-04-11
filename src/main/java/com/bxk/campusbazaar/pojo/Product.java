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

    private int nob;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", originalPrice=" + originalPrice +
                ", discountPrice=" + discountPrice +
                ", stock=" + stock +
                ", merchantId=" + merchantId +
                ", status=" + status +
                ", isBargain=" + isBargain +
                ", condition='" + condition + '\'' +
                ", description='" + description + '\'' +
                ", nob=" + nob +
                '}';
    }
}