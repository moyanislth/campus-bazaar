package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.Coupon;
import java.util.List;

public interface CouponMapper {
    int insert(Coupon record);
    List<Coupon> selectAll();

}