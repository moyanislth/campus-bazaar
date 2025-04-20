package com.bxk.campusbazaar.api.service;

import com.bxk.campusbazaar.pojo.Order;

public interface OrderService {

    Integer getOrderStatus(int id);

    Order getById(String id);

}
