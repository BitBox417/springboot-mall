//package com.trevor.springbootmall.service.impl;
//
//import com.trevor.springbootmall.dao.UserDao;
//import com.trevor.springbootmall.dto.UserRegisterRequest;
//import com.trevor.springbootmall.model.User;
//import com.trevor.springbootmall.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//@Component
//public class UserServiceImpl implements UserService {
//
//    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
//
//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    public Integer register(UserRegisterRequest userRegisterRequest) {
////        檢查註冊的Email
//      User user =  userDao.getUserByEmail(userRegisterRequest.getEmail());
//
//      if (user!=null) {
//          log.warn("該Email {} 已經被註冊", userRegisterRequest.getEmail());
//          throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//      }
//
////          創建帳號
//        return userDao.createUser(userRegisterRequest);
//    }
//
//    @Override
//    public User getUserById(Integer userId) {
//        return userDao.getUserById(userId);
//    }
//}
