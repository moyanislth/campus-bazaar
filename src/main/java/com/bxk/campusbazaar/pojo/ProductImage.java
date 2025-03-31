package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductImage {
    private Long id;

    private Long productId;

    private String imageUrl;

    private Boolean isMain;

}