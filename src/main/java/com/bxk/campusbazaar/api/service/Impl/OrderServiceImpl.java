package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.mapper.OrderMapper;
import com.bxk.campusbazaar.api.service.OrderService;
import com.bxk.campusbazaar.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Integer getOrderStatus(int id) {

        return orderMapper.selectStatusByPrimaryKey(id);
    }

    @Override
    public Order getById(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}
