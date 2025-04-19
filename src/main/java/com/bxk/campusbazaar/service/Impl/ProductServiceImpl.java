package com.bxk.campusbazaar.service.Impl;

import com.bxk.campusbazaar.mapper.ProductMapper;
import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Autowired
    ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAll();
    }

    @Override
    public Product getProductById(int id) {
        return productMapper.selectByPrimaryKey((long) id);
    }

    @Override
    public void updateProductNob(int id) {
        Product pr = productMapper.selectByPrimaryKey((long) id);
        int nob = pr.getNob() + 1;
        productMapper.updateNobByPrimaryKey((long)id,nob);
    }

    @Override
    public List<Product> getProductByLike(String name, String standard, boolean ascending) {
        // 校验排序字段合法性（防止SQL注入）
        if (!List.of("name", "price", "nob").contains(standard)) {
            standard = "name"; // 默认排序字段
        }
        return productMapper.selectByLikeName(name, standard, ascending);
    }

    @Override
    public void updateProductStatus(int id, int status) {
        productMapper.updateProductStatusByPrimaryKey(id,status);
    }

    @Override
    public void updateProductStatus() {
        productMapper.updateProductStatus();
    }

    @Override
    public Object test(String name) {
        return productMapper.test(name);
    }
}
