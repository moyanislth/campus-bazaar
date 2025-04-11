package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.Product;
import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    void updateNobByPrimaryKey(Long id , int nob);

    List<Product> selectProductByLike(String name, String standard, boolean ascending);

    void updateProductStatusByPrimaryKey(int id, int status);

    void updateProductStatus();
}