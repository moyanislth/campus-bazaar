package com.bxk.campusbazaar.service;


import com.bxk.campusbazaar.pojo.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> listCoupon();

    void addCoupon(Coupon coupon);
}
