package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.UserCoupon;
import java.util.List;

public interface UserCouponMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCoupon record);

    UserCoupon selectByPrimaryKey(Long id);

    List<UserCoupon> selectAll();

    int updateByPrimaryKey(UserCoupon record);
}