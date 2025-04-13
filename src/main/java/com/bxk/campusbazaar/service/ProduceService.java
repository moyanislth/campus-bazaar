package com.bxk.campusbazaar.service;

import com.bxk.campusbazaar.pojo.Product;

import java.util.List;

public interface ProduceService {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    void updateProductNob(int id);

    List<Product> getProductByLike(String name, String standard, boolean ascending);

    void updateProductStatus(int id, int status);
    void updateProductStatus();
    Object test(String name);
}
