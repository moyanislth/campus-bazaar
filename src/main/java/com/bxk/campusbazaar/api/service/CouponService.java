package com.bxk.campusbazaar.api.service;


import com.bxk.campusbazaar.pojo.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> listCoupon();

    void addCoupon(Coupon coupon);
    Coupon findCouponById(int id);
}
