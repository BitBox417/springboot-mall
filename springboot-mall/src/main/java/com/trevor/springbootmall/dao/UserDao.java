package com.trevor.springbootmall.dao;

import com.trevor.springbootmall.dto.UserRegisterRequest;
import com.trevor.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);

//    User getUserByEmail(String email);

//    Integer createUser(UserRegisterRequest userRegisterRequest);


}
