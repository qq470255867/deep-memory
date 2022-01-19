package com.icycraft.mymem.service;

import com.icycraft.mymem.entity.User;
import org.springframework.stereotype.Repository;


public interface UserService {

    User getUserById(long id);

    String getOpenId(String code) throws Exception;

    User login(String openId);

    User updateUser(User user);

    int addExp(long userId,int exp);
}
