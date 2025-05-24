package com.bxk.campusbazaar.api.controller.common;

import com.bxk.campusbazaar.Enum.PhotoType;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @PostMapping("/upload_license")
    public Response<Object> uploadLicense(@RequestParam MultipartFile file) {

        return upload(file, PhotoType.Licence);
    }
    @PostMapping("/upload_idCard")
    public Response<Object> uploadIdCard(@RequestParam MultipartFile file) {

        return upload(file, PhotoType.IdCard);
    }

    /**
     * 根据文件类型不同上传到不同文件夹
     * @param file 接收到的文件
     * @param type 文件类型,枚举类型
     * @return Response响应
     */
    private Response<Object> upload(MultipartFile file, PhotoType type) {
        // 验证文件可用性
        if (file == null) {
            return Response.fail("文件不能为空");
        }
        if (file.getSize() > 1024 * 1024 * 20) {
            return Response.fail("文件大小不能超过20M");
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"jpg,jpeg,png".toUpperCase().contains(suffix.toUpperCase())) {
            return Response.fail("请上传正确的图片格式(jpg,jpeg,png)");
        }

        // 根据图片不同类型上传到不同文件夹
        String savePath = UPLOAD_FOLDER + "/" + type.toString() + "/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            boolean saved = savePathFile.mkdir();
             if (!saved) {
                 return Response.fail("文件上传失败");
             }
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            log.error(e);
            return Response.fail("文件上传失败");
        }

        //返回文件名称
        return Response.success(savePath + filename);
    }
}
