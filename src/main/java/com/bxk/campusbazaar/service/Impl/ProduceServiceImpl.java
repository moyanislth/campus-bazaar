package com.bxk.campusbazaar.service.Impl;

import com.bxk.campusbazaar.mapper.ProductMapper;
import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduceServiceImpl implements ProduceService {

    private final ProductMapper productMapper;

    @Autowired
    ProduceServiceImpl(ProductMapper productMapper) {
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
        return productMapper.selectProductByLike(name,standard,ascending);
    }

    @Override
    public void updateProductStatus(int id, int status) {
        productMapper.updateProductStatusByPrimaryKey(id,status);
    }

    @Override
    public void updateProductStatus() {
        productMapper.updateProductStatus();
    }

}
