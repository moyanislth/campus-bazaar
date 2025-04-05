package com.bxk.campusbazaar.service.Impl;

import com.bxk.campusbazaar.mapper.UserMapper;
import com.bxk.campusbazaar.pojo.User;
import com.bxk.campusbazaar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserMapper userMapper;

    @Autowired
    UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> getAllUser() {

        return userMapper.selectAll();
    }

    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public void deleteAll() {
        userMapper.deleteAll();
    }
}
