package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.controller.common.FileController;
import com.bxk.campusbazaar.api.mapper.MerchantMapper;
import com.bxk.campusbazaar.api.mapper.UserMapper;
import com.bxk.campusbazaar.api.service.UserService;
import com.bxk.campusbazaar.pojo.Merchant;
import com.bxk.campusbazaar.pojo.User;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    UserMapper userMapper;
    MerchantMapper merchantMapper;
    FileController fileController;

    @Autowired
    UserServiceImpl(UserMapper userMapper, MerchantMapper merchantMapper, FileController fileController) {
        this.userMapper = userMapper;
        this.merchantMapper = merchantMapper;
        this.fileController = fileController;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> getAllUser() {

        return userMapper.selectAll();
    }

    /**
     * 注册用户
     * 商家用户需要额外提供商家营业执照和身份证
     * 加锁保证两个表数据同步
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(User user, int role, MultipartFile license, MultipartFile idCard) {
        // 1. 插入用户（此时事务未提交，但 user.id 已被回填）
        userMapper.insert(user); // 依赖 useGeneratedKeys 配置

        log.info(user.toString());
        // 2. 验证 ID 是否回填
        if (user.getId() == null) {
            throw new IllegalStateException("用户ID获取失败，请检查Mapper配置");
        }

        // 3. 商家注册逻辑
        if (role == 1) {
            Merchant merchant = buildMerchant(user, license, idCard);
            merchantMapper.insert(merchant);
        }

    }

    /**
     * 注册商家信息, 主要是初始化商家数据与收集验证信息
     * @param user 注册用户
     * @param license 商家营业执照
     * @param idCard 商家身份证
     * @return 商家对象
     */
    private Merchant buildMerchant(User user, MultipartFile license, MultipartFile idCard) {

        Response<Object> licenseResponse = fileController.uploadLicense(license);
        Response<Object> idCardResponse = fileController.uploadIdCard(idCard);

        // 文件上传（需确保上传失败时抛出异常）
        if (licenseResponse.getCode() != 200) {
            throw new IllegalStateException("营业执照:" + licenseResponse.getData().toString());
        } else if (idCardResponse.getCode() != 200) {
            throw new IllegalStateException("身份证:" + idCardResponse.getData().toString());
        }

        String licenseUrl = licenseResponse.getData().toString();
        String idCardUrl = idCardResponse.getData().toString();

        // 构建商家对象
        Merchant merchant = new Merchant();
        // 绑定用户
        merchant.setUserId(user.getId());
        // 初始化数据
        // 商家等级默认为1, 钱包余额默认为0, 总销售额默认为0
        merchant.setLevel((byte) 1);
        merchant.setWalletBalance(BigDecimal.ZERO);
        merchant.setTotalSales(0);
        // 设置商家信息存储地址(D:\Photos\CB)
        merchant.setLicenseImg(licenseUrl);
        merchant.setIdCardImg(idCardUrl);

        return merchant;
    }

    @Override
    public void deleteAll() {
        userMapper.deleteAll();
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey((long) id);
    }

    @Override
    public void updateStatus(int selectedId, Byte status) {
        User user = userMapper.selectByPrimaryKey((long) selectedId);
        user.setStatus(status);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    // 重置密码
    @Override
    public void updatePassword(long id, String md5String) {
        userMapper.updatePassword(id, md5String);
    }
}
