package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    Order selectByPrimaryKey(String id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}