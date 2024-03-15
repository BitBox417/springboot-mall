package com.trevor.springbootmall.dao.impl;

import com.trevor.springbootmall.dao.UserDao;
import com.trevor.springbootmall.dto.UserRegisterRequest;
import com.trevor.springbootmall.model.User;
import com.trevor.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public User getUserById(Integer userId) {
        //                String sql = "SELECT memberid, password, created_date, last_modified_date " +
        String sql = "SELECT memberid FROM [members] WHERE memberid = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

//    @Override
//    public User getUserByEmail(String email) {
//        String sql = "SELECT memberid, email, password, created_date, last_modified_date " +
//                "FROM [members] WHERE email = :email"; // 注意[members]的使用
//        Map<String, Object> map = new HashMap<>();
//        map.put("email", email);
//
//        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
//        if (userList.size() > 0) {
//            return userList.get(0);
//        } else {
//            return null;
//        }
//    }



//    @Override
//    public Integer createUser(UserRegisterRequest userRegisterRequest) {
//        String sql = "INSERT INTO [members](password, created_date, last_modified_date) " + // 修改这里
//                "VALUES (:password, :createdDate, :lastModifiedDate)"; // 这里添加了闭合括号
//
//        Map<String, Object> map = new HashMap<>();
////        map.put("email", userRegisterRequest.getEmail());
//        map.put("password", userRegisterRequest.getPassword());
//        Date now = new Date();
//        map.put("createdDate", now);
//        map.put("lastModifiedDate", now);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//        int userId = keyHolder.getKey().intValue();
//        return userId;
//    }

}
