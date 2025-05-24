package com.bxk.campusbazaar.api.controller.common;

import com.bxk.campusbazaar.pojo.Coupon;
import com.bxk.campusbazaar.api.service.CouponService;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/coupon")
@CrossOrigin
class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * 获取优惠券列表
     * @return 优惠券列表
     */
    @GetMapping("/list")
    public Response<Object> listCoupon() {
        List<Coupon> coupons = couponService.listCoupon();

        return Response.success(coupons);
    }

    /**
     * 获取用户所有
     * @param id 用户id
     * @return 优惠券列表
     */
    // TODO: 需要实现用户优惠券列表

    /**
     * 添加优惠券
     * @param name           优惠券名称
     * @param type           类型：0-满减，1-折扣
     * @param value          面值（满减为金额，折扣为百分比，如0.8表示8折）
     * @param minAmount      最低消费金额（满减券需满足此金额才能使用）
     * @param startTime      生效时间
     * @param endTime        过期时间
     * @param totalCount     总发放数量（-1表示不限量）
     * @param createdBy      创建人（管理员ID）
     */
    @PostMapping("/add")
    public Response<Object> addCoupon(@RequestParam String name,
                                      @RequestParam Byte type,
                                      @RequestParam BigDecimal value,
                                      @RequestParam(required = false) BigDecimal minAmount,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                      @RequestParam(required = false) Integer totalCount,
                                      @RequestParam Long createdBy) {
        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setType(type);
        coupon.setValue(value);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        coupon.setCreatedBy(createdBy);
        // 最低消费金额如果设置为null,数据库默认0.00
        if (minAmount != null) {
            coupon.setMinAmount(minAmount);
        }
        // 刚发优惠卷, 总发放数量即为剩余数量
        if (totalCount != null) {
            coupon.setTotalCount(totalCount);
            coupon.setRemainingCount(totalCount);
        }

        couponService.addCoupon(coupon);

        return Response.success();
    }

}
