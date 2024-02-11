package com.trevor.springbootmall.service.impl;

import com.trevor.springbootmall.dao.UserDao;
import com.trevor.springbootmall.dto.UserRegisterRequest;
import com.trevor.springbootmall.model.User;
import com.trevor.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
