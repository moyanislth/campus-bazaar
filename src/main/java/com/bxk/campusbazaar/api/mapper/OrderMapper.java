package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    Order selectByPrimaryKey(String id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Integer selectStatusByPrimaryKey(int id);
}
