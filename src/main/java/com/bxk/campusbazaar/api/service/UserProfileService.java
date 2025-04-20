package com.bxk.campusbazaar.api.service;

public interface UserProfileService {

    Object getProfile(long id);

    void updateProfile(String avatar, String bio, String wechat, String deliveryAddress);
}
