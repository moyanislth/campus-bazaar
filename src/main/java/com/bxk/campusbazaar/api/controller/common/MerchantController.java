package com.bxk.campusbazaar.api.controller.common;


import com.bxk.campusbazaar.api.service.MerchantService;
import com.bxk.campusbazaar.pojo.DTO.MerchantCredentialsDTO;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/merchant")
@CrossOrigin
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * 获取商户证件信息（符合自定义Response规范）
     * @param id 商户ID
     * @return Response对象，包含证件信息
     */
    @GetMapping("/getMerchantCredentials")
    public Response<Object> getCredentials(@RequestParam("userId") int id){

        MerchantCredentialsDTO credentialsDTO = merchantService.getMerchantCredentials(id);

        if (credentialsDTO == null) {
            return Response.fail("商户证件信息不存在");
        }

        Map<String, byte[]> credentials = new HashMap<>();

        // 读取许可证图片
        Path licensePath = Paths.get(credentialsDTO.licenseImgUrl());
        if (!Files.exists(licensePath)) {
            return Response.fail( "许可证文件未找到");
        }

        // 读取图片转换成byte[]
        byte[] licenseBytes;
        try {
            licenseBytes = Files.readAllBytes(licensePath);
        } catch (IOException e) {
            log.error("读取许可证文件失败", e);
            return Response.fail("读取许可证文件失败");
        }
        credentials.put("license", licenseBytes);

        // 读取身份证图片
        Path idCardPath = Paths.get(credentialsDTO.idCardImgUrl());
        if (!Files.exists(idCardPath)) {
            return Response.fail( "身份证文件未找到");
        }

        // 读取图片转换成byte[]
        byte[] idCardBytes;
        try {
            idCardBytes = Files.readAllBytes(idCardPath);
        } catch (IOException e) {
            log.error("读取身份证文件失败", e);
            return Response.fail("读取身份证文件失败");
        }

        credentials.put("idCard", idCardBytes);

        return Response.success(credentials);

    }

    /**
     * 获取商户名称
     * @param id 商户ID
     * @return Response对象，包含商户名称
     */
    @GetMapping("/getMerchantName")
    public Response<Object> getMerchantName(@RequestParam("merchantId") int id) {
        String merchantName = merchantService.getMerchantName(id);
        if (merchantName == null) {
            return Response.success('-');
        }
        return Response.success(merchantName);
    }
}


