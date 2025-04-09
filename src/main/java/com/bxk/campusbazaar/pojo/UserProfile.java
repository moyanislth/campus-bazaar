package com.bxk.campusbazaar.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户附属信息
 *  头像、简介、微信、收货地址（json格式）
 */
@Getter
@Setter
@TableName(value = "user_profile", autoResultMap = true)
public class UserProfile {
    private Long userId;
    private String avatar;
    private String bio;
    private String wechat;
    private List<String> deliveryAddress;

}
