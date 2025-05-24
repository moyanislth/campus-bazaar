package com.bxk.campusbazaar.api.controller.common;

import com.bxk.campusbazaar.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/order")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/oderStatus")
    public Integer getOrderStatus(@RequestParam int id) {
        return orderService.getOrderStatus(id);
    }
}
