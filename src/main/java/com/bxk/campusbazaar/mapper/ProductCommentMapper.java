package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.ProductComment;
import java.util.List;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductComment record);

    ProductComment selectByPrimaryKey(Long id);

    List<ProductComment> selectAll();

    int updateByPrimaryKey(ProductComment record);
}