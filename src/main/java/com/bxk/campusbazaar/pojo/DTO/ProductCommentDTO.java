package com.bxk.campusbazaar.pojo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCommentDTO {
    public Long id;

    public Long productId;

    public Long userId;
    public String userName;

    public Byte starRating;

    public Date createdAt;
    public String createdAtStr;

    public String content;
}
