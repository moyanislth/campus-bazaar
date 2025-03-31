package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.OrderItem;
import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    List<OrderItem> selectAll();

    int updateByPrimaryKey(OrderItem record);
}