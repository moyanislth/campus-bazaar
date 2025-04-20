package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.MerchantLevel;
import java.util.List;

public interface MerchantLevelMapper {
    int deleteByPrimaryKey(Byte level);

    int insert(MerchantLevel record);

    MerchantLevel selectByPrimaryKey(Byte level);

    List<MerchantLevel> selectAll();

    int updateByPrimaryKey(MerchantLevel record);
}
