package com.bxk.campusbazaar.Enum;

import lombok.Getter;

/**
 * UNPAID-待支付，PAID-已支付，SHIPPED-已发货，FINISHED-已完成，RETURNED-已退货
 */
@Getter
public enum OrderStatus {
    UNPAID(0),   // UNPAID 对应 0
    PAID(1),     // PAID 对应 1
    SHIPPED(2),  // SHIPPED 对应 2
    FINISHED(3), // FINISHED 对应 3
    RETURNED(4); // RETURNED 对应 4

    // 获取数字值
    private final int code; // 存储数字值

    // 构造方法（私有）
    OrderStatus(int code) {
        this.code = code;
    }

}
