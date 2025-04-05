package com.bxk.campusbazaar.controller;

import com.bxk.campusbazaar.pojo.User;
import com.bxk.campusbazaar.service.UserService;
import com.bxk.campusbazaar.tools.Md5Util;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Component
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

//调试代码-------------------------------------------------------------------------------
    /**
     * @调试方法
     * 获取所有用户信息
     */
    @GetMapping("/getAllUser")
    public List<User> getAll(){
        return userService.getAllUser();
    }
    /**
     * @调试方法
     * 删除所有用户及其关联表信息
     */
    @PostMapping(value = "/deleteAll")
    public Response<Object> deleteAll(){
        try {
            userService.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Response.success();
    }

//业务代码---------------------------------------------------------------------------
    @PostMapping(value = "/register")
    public Response<Object> register(@RequestParam String username, @RequestParam String password, @RequestParam Byte role, @RequestParam String phone){

        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPhone(phone);

        // 默认刚注册用户状态为 0-待审核
        user.setStatus((byte) 0);

        // 密码加密存储
        password = Md5Util.getMD5String(password);
        log.info("password:"+password);
        user.setPassword(password);

        try {
            userService.register(user);
        } catch (Exception e) {
            // 报告手机号重复
            return Response.fail(e.getCause().getMessage());
        }

        return Response.success();
    }

    @PostMapping(value = "/login")
    public Response<Object> login(@RequestParam String username, @RequestParam String password){

        User user = userService.getUserByUsername(username);

        if(user == null){
            return Response.fail("用户不存在");
        } else if (!user.getPassword().equals(Md5Util.getMD5String(password))){
            return Response.fail("密码错误");
        }

        return Response.success();
    }

}

