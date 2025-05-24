package com.bxk.campusbazaar.api.service;

import com.bxk.campusbazaar.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {

    User getUserByUsername(String username);

    List<User> getUserByLikeName(String keyword);

    List<User> getAllUser();
    void register(User user, int role, MultipartFile license, MultipartFile idCard);

    void deleteAll();

    User getUserById(long id);
    String getUserNameById(long id);
    void updateStatus(int selectedId, Byte status);

    User getUserByPhone(String phone);

    void updatePassword(long id, String md5String);

    List<User> searchUsers(String keyword, Byte status);
}
