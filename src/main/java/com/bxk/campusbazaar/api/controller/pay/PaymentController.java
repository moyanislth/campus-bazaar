package com.bxk.campusbazaar.api.controller.pay;

import com.bxk.campusbazaar.Enum.OrderStatus;
import com.bxk.campusbazaar.Enum.PaymentMethod;
import com.bxk.campusbazaar.api.service.CouponService;
import com.bxk.campusbazaar.api.service.OrderService;
import com.bxk.campusbazaar.api.service.WalletService;
import com.bxk.campusbazaar.pojo.Coupon;
import com.bxk.campusbazaar.pojo.Order;
import com.bxk.campusbazaar.pojo.PaymentRequest;
import com.bxk.campusbazaar.tools.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final OrderService orderService;
    private final WalletService walletService;
    private final CouponService couponService;

    @Autowired
    PaymentController(OrderService orderService, WalletService walletService, CouponService couponService) {
        this.orderService = orderService;
        this.walletService = walletService;
        this.couponService = couponService;
    }

    // 模拟支付
    @PostMapping("/pay")
    public Response<Object> pay(@RequestBody @Validated PaymentRequest paymentRequest) {
        Order order = orderService.getById(paymentRequest.getOrderId());

        // 简单校验
        if (order == null ) {
            return Response.fail("订单不存在");
        }
        if (order.getStatus() != OrderStatus.UNPAID.getCode()) {
            return Response.fail("订单状态异常");
        }

        // 计算实际支付金额
        BigDecimal actualAmount = order.getTotalAmount();
        if (paymentRequest.getCouponId() != null) {
            Coupon coupon = couponService.findCouponById(Math.toIntExact(paymentRequest.getCouponId()));

            // 满减 OR 折扣
            if(coupon.getType() == 0){
                if (coupon.getMinAmount() != null && coupon.getMinAmount().compareTo(actualAmount) < 0) {
                    actualAmount = actualAmount.subtract(coupon.getValue());
                } else if (coupon.getMinAmount() == null) {
                    actualAmount = actualAmount.subtract(coupon.getValue());
                    if (actualAmount.compareTo(BigDecimal.ZERO) <= 0){
                        actualAmount = BigDecimal.ZERO;
                    }else {
                        actualAmount = actualAmount.subtract(coupon.getValue());
                    }
                }
            } else if (coupon.getType() == 1) {
                if (coupon.getMinAmount() != null && coupon.getMinAmount().compareTo(actualAmount) > 0) {
                    actualAmount = actualAmount.multiply(coupon.getValue());
                }
            }
        }

        // 判断余额
        if (paymentRequest.getPaymentMethod() == PaymentMethod.wallet) {
            // TODO
            int currentUserId = 1;
            walletService.deduct(currentUserId, actualAmount);
        }

        return Response.success("支付成功");
    }

    /**
     * 支付状态查询
     * 状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已退货
     */
    @GetMapping("/status")
    public Response<Object> checkPaymentStatus(@RequestParam String orderId) {

        if (orderId == null || orderId.isEmpty()) {
            return Response.fail();
        }

        Integer status = orderService.getOrderStatus(Integer.parseInt(orderId));
        return switch (status) {
            case 0 -> Response.success("待支付");
            case 1 -> Response.success("已支付");
            case 2 -> Response.success("已发货");
            case 3 -> Response.success("已完成");
            case 4 -> Response.success("已退货");
            default -> Response.fail();
        };

    }
}
