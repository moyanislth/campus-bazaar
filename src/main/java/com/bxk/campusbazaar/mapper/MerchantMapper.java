package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.Merchant;
import java.util.List;

public interface MerchantMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Merchant record);

    Merchant selectByPrimaryKey(Long userId);

    List<Merchant> selectAll();

    int updateByPrimaryKey(Merchant record);
}