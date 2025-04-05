package com.bxk.campusbazaar.mapper;

import com.bxk.campusbazaar.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    void deleteAll();

    User selectByUsername(String username);
}