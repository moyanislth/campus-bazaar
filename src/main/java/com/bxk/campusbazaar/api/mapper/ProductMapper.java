package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.pojo.ProductImage;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectByStatus(Byte status);

    List<ProductImage> selectProductImgs(long id);

    List<Product> selectAll();

    void updateNobByPrimaryKey(Long id , int nob);

    void updateProductStatusByPrimaryKey(int id, int status);

    void updateProductStatus();

    Object test(String name);

    List<Product> selectByLikeName(String name);
}
