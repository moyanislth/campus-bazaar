package com.bxk.campusbazaar.api.service;

import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.pojo.ProductComment;
import com.bxk.campusbazaar.pojo.ProductImage;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    List<ProductImage> getProductImgs(Long id);

    void updateProductNob(int id);
    void addComment(ProductComment productComment);
    List<ProductComment> getCommentsByProductId(Long id);
    List<Product> getProductByLike(String name, Byte status);

    void updateProductStatus(int id, int status);
    void updateProductStatus();
    Object test(String name);
}
