package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Wallet record);

    Wallet selectByPrimaryKey(Long userId);

    List<Wallet> selectAll();

    int updateByPrimaryKey(Wallet record);

}
