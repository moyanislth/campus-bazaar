package com.bxk.campusbazaar.api.controller.common;

import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.api.service.ProductService;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/test")
    public Response<Object> getDatabase(@RequestParam String name){
        String result = (String) productService.test(name);
        System.out.println(result);
        return Response.success(result);
    }

    @GetMapping("/getAllProducts")
    public Response<Object> getAllProducts(){

        List<Product> products = productService.getAllProducts();

        return Response.success(products);
    }

    /**
     * 根据id获取商品
     */
    @GetMapping("/getProductById")
    public Response<Object> getProductById(@RequestParam("id") int id){
        Product product = productService.getProductById(id);
        return Response.success(product);
    }

    /**
     * 根据商品名模糊查询
     * @param name 商品名
     * @param standard 排序标准(商品名称name, 商品价格price, 商品购买人数nob)
     * @param ascending 是否升序
     * @return 商品列表
     */
    @GetMapping("/getProductByLike")
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "name") String standard,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.getProductByLike(name, standard, ascending);
    }

    /**
     * 购买人数++
     */
    @PatchMapping("/updateProductNob")
    public Response<Object> updateProductNob(@RequestParam int id){
        try {
            productService.updateProductNob(id);
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }

    @PatchMapping("updateProductStatus")
    public Response<Object> updateProductStatus(@RequestParam int id, @RequestParam int status){
        if (!Arrays.asList(0,1,2).contains(status)){
            return Response.fail("商品状态只能为0,1或2");
        }
        try {
            productService.updateProductStatus(id,status);
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }
    @PatchMapping("updateAllProductStatus")
    public Response<Object> updateProductStatus(){
        try {
            productService.updateProductStatus();
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
    }

    /**
     * 添加商品
     * 商品状态和购买人数数据库默认0，不覆盖赋值
     */
    @PostMapping("/addProduct")
    public Response<Object> addProduct(@RequestBody Product product){

        log.info(product.toString());

        try {
            productService.addProduct(product);
        }catch (Exception e){
            return Response.fail(e.getCause().getMessage());
        }
        return Response.success("添加商品成功");
    }
}
