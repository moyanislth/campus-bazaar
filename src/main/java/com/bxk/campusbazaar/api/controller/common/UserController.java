package com.bxk.campusbazaar.api.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.bxk.campusbazaar.api.service.UserService;
import com.bxk.campusbazaar.pojo.User;
import com.bxk.campusbazaar.tools.Md5Util;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Log4j2
@Component
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final WalletController walletController;

    @Autowired
    UserController(UserService userService, WalletController walletController){
        this.userService = userService;
        this.walletController = walletController;
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

    @GetMapping("/getUserById")
    public User getUserById(@RequestParam int id){
        return userService.getUserById(id);
    }

//业务代码---------------------------------------------------------------------------

    /**
     * 用户注册
     * @param username 姓名（必填）
     * @param password 密码（必填）
     * @param phone 手机号（必填）
     * @param email 邮箱
     * @param city 城市
     * @param gender 性别 0-未知 1-男 2-女
     * @param bankAccount 银行账户（16位）
     * @param role 角色 0-普通用户 1-商家 2-管理员
     */
    @PostMapping(value = "/register")
    public Response<Object> register(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String phone,
                                     @RequestParam Byte role,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) Byte gender,
                                     @RequestParam(required = false) String bankAccount,
                                     @RequestParam(name = "license", required = false) MultipartFile license,
                                     @RequestParam(name = "idCard", required = false) MultipartFile idCard){


        // 首先检查手机号是否已注册
        if (userService.getUserByPhone(phone) != null){
            return Response.fail("手机号已注册");
        }

        User user = new User();

        // 正常注册
        username = username.trim();
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        user.setCity(city);
        user.setGender(gender);
        user.setBankAccount(bankAccount);
        user.setCreatedAt(Instant.now());

        user.setRole(role);

        // 默认商户需要审核
        if (role == 1){
            user.setStatus((byte) 0);
        }else {
            user.setStatus((byte) 1);
        }


        // 密码加密存储
        password = Md5Util.getMD5String(password);
        user.setPassword(password);

        try {
            userService.register(user, (int)role, license, idCard);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }

        // 注册完成初始化钱包数据
        walletController.newWallet(Math.toIntExact(user.getId()));

        return Response.success();
    }

    @PostMapping(value = "/login")
    public Response<Object> login(@RequestBody String loginData){

        JSONObject jsonObject = JSONObject.parseObject(loginData);
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");

        log.debug("phone: " + jsonObject.getString("phone"));
        log.debug("password: " + jsonObject.getString("password"));

        User user = userService.getUserByPhone(phone);

        if(user == null || !user.getPassword().equals(Md5Util.getMD5String(password))){
            return Response.fail("用户名或密码错误");
        }

        // 检查用户状态,需要管理员审核能否通过注册
        if (user.getStatus() == 0){
            return Response.fail("用户待审核");
        }

        return Response.success();
    }

    /**
     * 管理员审核用户
     * @param adminId 管理员id
     * @param status 状态 0-待审核 1-通过 2-封禁, 前端负责校验
     * @param selectedId 用户id
     * @return Response<Object>
     */
    @PatchMapping(value = "/updateStatus")
    public Response<Object> update(@RequestParam int adminId ,
                                   @RequestParam Byte status,
                                   @RequestParam int selectedId){
        try{
            if (userService.getUserById(adminId).getRole() == 2){
                if (userService.getUserById(selectedId).getRole() == 2){
                    return Response.fail("管理员不能被修改");
                }

                userService.updateStatus(selectedId, status);
                return Response.success();
            }
        }catch (Exception e){
            return Response.fail("用户不存在");
        }

        return Response.fail("权限不足");
    }

    @PatchMapping(value = "/updatePassword")
    public Response<Object> updatePassword(@RequestParam int id, @RequestParam String password){
        try {
            userService.updatePassword(id, Md5Util.getMD5String(password));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }

        return Response.success();
    }
}

