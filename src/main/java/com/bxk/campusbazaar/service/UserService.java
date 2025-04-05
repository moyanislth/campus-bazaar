package com.bxk.campusbazaar.service;

import com.bxk.campusbazaar.pojo.User;

import java.util.List;


public interface UserService {
    User getUserByUsername(String username);

    List<User> getAllUser();

    int register(User user);

    void deleteAll();
}
