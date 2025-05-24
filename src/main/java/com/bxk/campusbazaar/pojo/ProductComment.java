package com.bxk.campusbazaar.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Setter
@Getter
public class ProductComment {
    private Long id;

    private Long productId;

    private Long userId;

    private Byte starRating;

    private Date createdAt;

    private String content;

}
