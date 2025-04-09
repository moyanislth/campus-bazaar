package com.bxk.campusbazaar.controller.common;

import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Component
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @GetMapping("/getAllProducts")
    public Response<Object> getAllProducts(){
        return Response.success("获取所有商品成功");
    }

    @PostMapping("/addProduct")
    public Response<Object> addProduct(){
        return Response.success("添加商品成功");
    }
}
