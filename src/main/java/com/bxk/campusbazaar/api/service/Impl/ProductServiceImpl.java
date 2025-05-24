package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.mapper.ProductCommentMapper;
import com.bxk.campusbazaar.api.mapper.UserMapper;
import com.bxk.campusbazaar.api.service.ProductService;
import com.bxk.campusbazaar.api.mapper.ProductMapper;
import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.pojo.ProductComment;
import com.bxk.campusbazaar.pojo.ProductImage;
import com.bxk.campusbazaar.pojo.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductCommentMapper productCommentMapper;
    private final UserMapper userMapper;

    @Autowired
    ProductServiceImpl(ProductMapper productMapper, UserMapper userMapper,ProductCommentMapper productCommentMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.productCommentMapper = productCommentMapper;
    }

    @Override
    public void addProduct(Product product) {
        productMapper.insert(product);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productMapper.selectAll();
        // 添加参数merchant_name
        addUserName(products);
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return productMapper.selectByPrimaryKey((long) id);
    }

    @Override
    public List<ProductImage> getProductImgs(Long id) {
        return productMapper.selectProductImgs(id);
    }

    @Override
    public void updateProductNob(int id) {
        Product pr = productMapper.selectByPrimaryKey((long) id);
        int nob = pr.getNob() + 1;
        productMapper.updateNobByPrimaryKey((long)id,nob);
    }

    @Override
    public void addComment(ProductComment productComment) {
        productCommentMapper.insert(productComment);
    }

    @Override
    public List<ProductComment> getCommentsByProductId(Long id) {
        return productCommentMapper.selectByProductId(id);
    }

    @Override
    public List<Product> getProductByLike(String name, Byte status) {

        List<Product> products;

        if (name != null) {
            products = productMapper.selectByLikeName(name);

            if (status != null) {
                products.removeIf(user -> !user.getStatus().equals(status));
            }

        } else if (status != null) {
            products = productMapper.selectByStatus(status);
        } else {
            products = productMapper.selectAll();
        }

        // 添加参数merchant_name
        addUserName(products);

        return products;
    }
    /**
     * 补充商户信息
     */
    private void addUserName(List<Product> products){
        for (Product product : products) {
            User user = userMapper.selectByPrimaryKey(product.getMerchantId());

            product.setMerchantName(user.getUsername());
        }
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
