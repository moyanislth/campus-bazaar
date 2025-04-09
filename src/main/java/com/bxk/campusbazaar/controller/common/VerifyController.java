package com.bxk.campusbazaar.controller.common;


import com.bxk.campusbazaar.tools.VerifyUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * 验证码验证控制器
 */
@RestController
@RequestMapping("/api/verify")
public class VerifyController {

    /**
     * 生成验证码的接口
     *
     * @param response Response对象
     * @param request  Request对象
     */
    @PostMapping("/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 获取到session
        HttpSession session = request.getSession();
        // 取到sessionid
        String id = session.getId();

        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.newBuilder()
                .setWidth(120)   //设置图片的宽度
                .setHeight(35)   //设置图片的高度
                .setSize(4)      //设置字符的个数
                .setLines(10)    //设置干扰线的条数
                .setFontSize(25) //设置字体的大小
                .setTilt(false)   //设置是否需要倾斜
                .setBackgroundColor(Color.LIGHT_GRAY) //设置验证码的背景颜色
                .build()         //构建VerifyUtil项目
                .createImage();  //生成图片
        // 将验证码存入Session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, objs[0]);
        // 打印验证码
        System.out.println(objs[0]);


        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    /**
     * 业务接口包含了验证码的验证
     *
     * @param code    前端传入的验证码
     * @param request Request对象
     */
    @GetMapping("/checkcode")
    public boolean checkCode(String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = session.getId();

        // 将session中的取出对应session id生成的验证码
        String serverCode = (String) session.getAttribute("SESSION_VERIFY_CODE_" + id);
        // 校验验证码
        if (null == serverCode || !serverCode.equalsIgnoreCase(code)) {
            return false;
        }

        return true;
    }
}
