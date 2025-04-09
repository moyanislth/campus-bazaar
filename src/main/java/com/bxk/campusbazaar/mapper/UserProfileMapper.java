package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.UserProfile;


public interface UserProfileMapper {

    int insert(UserProfile userProfile);

    int deleteByPrimaryKey(long id);

    UserProfile selectByPrimaryKey(long id);


    void updateProfile(String avatar, String bio, String wechat, String deliveryAddress);
}
