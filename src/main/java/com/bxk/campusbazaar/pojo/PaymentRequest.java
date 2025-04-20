package com.bxk.campusbazaar.pojo;

import com.bxk.campusbazaar.Enum.PaymentMethod;
import lombok.Data;
import lombok.NonNull;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class PaymentRequest {
    @NotBlank
    private String orderId;

    @NonNull
    private PaymentMethod paymentMethod; // 枚举类

    private Long couponId; // 可选
}
