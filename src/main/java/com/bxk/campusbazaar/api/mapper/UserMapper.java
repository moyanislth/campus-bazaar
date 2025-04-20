package com.bxk.campusbazaar.api.mapper;

import com.bxk.campusbazaar.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    void deleteAll();

    User selectByUsername(String username);

    User selectByPhone(String phone);

    void updatePassword(Long id, String password);
}
