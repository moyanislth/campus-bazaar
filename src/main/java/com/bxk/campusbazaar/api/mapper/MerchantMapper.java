package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.Merchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MerchantMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Merchant record);

    Merchant selectByPrimaryKey(Long userId);

    List<Merchant> selectAll();

    int updateByPrimaryKey(Merchant record);
}
