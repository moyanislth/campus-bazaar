package com.bxk.campusbazaar.api.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.bxk.campusbazaar.pojo.Product;
import com.bxk.campusbazaar.api.service.ProductService;
import com.bxk.campusbazaar.pojo.SearchDTO;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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


    /**
     * 测试方法：上架所有商品
     * @return Response<Object>
     */
    @PatchMapping("updateAllProductStatus")
    public Response<Object> updateProductStatus(){
        try {
            productService.updateProductStatus();
        }catch (Exception e){
            return Response.fail();
        }
        return Response.success();
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
//        76RT76T7
        Product product = productService.getProductById(id);
        return Response.success(product);
    }

    /**
     * 后台查询商品
     * @param searchData 搜索关键字和状态
     * @return 商品列表
     */
    @PostMapping("/searchProducts")
    public List<Product> searchProducts(@RequestBody SearchDTO searchData){
        List<Product> products;

        String name = null;
        Byte status = null;

        if (searchData.keyword!=null){
            name = searchData.keyword.trim();
        }

        if (searchData.status!=null){
            status = searchData.status;
        }

        products = productService.getProductByLike(name, status);

        return products;
    }


    /**
     * 获取商品图片
     * @param id
     * @return 商品图片二进制的数组
     */
    @GetMapping("/getProductImages")
    public Response<Object> getProductImages(@RequestParam int id){
        ArrayList<byte[]> images = new ArrayList<>();

        List<String> imgs = productService.getProductImgs(id);

        for (String img : imgs) {
            Path imgPath = Path.of(img);

            if (!imgPath.toFile().exists()) {
                return Response.fail("图片文件未找到");
            }

            // 读取图片转换成byte[]
            byte[] imageBytes;

            try {
                imageBytes = Files.readAllBytes(imgPath);
                images.add(imageBytes);
            } catch (IOException e) {
                log.error("读取许可证文件失败", e);
                return Response.fail("读取许可证文件失败");
            }

        }

        return Response.success(images);
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

    /**
     * 更新商品状态
     * @param id 商品id
     * @param status 商品状态
     * @return Response<Object>
     */
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
