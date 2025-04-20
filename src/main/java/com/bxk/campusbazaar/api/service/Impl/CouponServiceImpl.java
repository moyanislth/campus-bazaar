package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.service.CouponService;
import com.bxk.campusbazaar.api.mapper.CouponMapper;
import com.bxk.campusbazaar.pojo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private final CouponMapper couponMapper;

    @Autowired
    public CouponServiceImpl(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }


    @Override
    public List<Coupon> listCoupon() {

        return couponMapper.selectAll();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        couponMapper.insert(coupon);
    }

    @Override
    public Coupon findCouponById(int id) {
        return couponMapper.selectCouponById(id);
    }
}
