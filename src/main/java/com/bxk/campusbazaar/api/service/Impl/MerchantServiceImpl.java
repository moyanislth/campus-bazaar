package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.mapper.MerchantMapper;
import com.bxk.campusbazaar.api.service.MerchantService;
import com.bxk.campusbazaar.api.service.UserService;
import com.bxk.campusbazaar.pojo.Merchant;
import com.bxk.campusbazaar.pojo.DTO.MerchantCredentialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantMapper merchantMapper;
    private final UserService userService;
    @Autowired
    public MerchantServiceImpl(MerchantMapper merchantMapper, UserService userService) {
        this.merchantMapper = merchantMapper;
        this.userService = userService;
    }

    @Override
    public Merchant getMerchant(int id) {
        return merchantMapper.selectByPrimaryKey((long)id);
    }

    @Override
    public MerchantCredentialsDTO getMerchantCredentials(int id) {
        Merchant merchant = merchantMapper.selectByPrimaryKey((long)id);

        if (merchant != null) {
            String licenseImgUrl = merchant.getLicenseImg();
            String idCardImgUrl = merchant.getIdCardImg();
            return new MerchantCredentialsDTO(licenseImgUrl, idCardImgUrl);
        }

        return null;
    }

    @Override
    public String getMerchantName(int id) {
        return userService.getUserById(id).getUsername();
    }
}
