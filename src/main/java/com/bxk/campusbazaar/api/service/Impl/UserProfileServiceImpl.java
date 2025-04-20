package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.mapper.UserProfileMapper;
import com.bxk.campusbazaar.api.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;

    @Autowired
    public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public Object getProfile(long id) {
        return userProfileMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateProfile(String avatar, String bio, String wechat, String deliveryAddress) {
        userProfileMapper.updateProfile(avatar, bio, wechat, deliveryAddress);
    }
}
