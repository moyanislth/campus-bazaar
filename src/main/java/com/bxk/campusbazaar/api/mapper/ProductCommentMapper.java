package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.ProductComment;
import java.util.List;

public interface ProductCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductComment record);

    ProductComment selectByPrimaryKey(Long id);

    List<ProductComment> selectAll();
    List<ProductComment> selectByProductId(Long id);
    int updateByPrimaryKey(ProductComment record);
}
