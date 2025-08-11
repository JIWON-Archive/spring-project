package com.devopscat.springproject.service;

import com.devopscat.springproject.entity.User;
import com.devopscat.springproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
    public String selectWriter(String writer) {
        return userMapper.selectWriter(writer);
    }
}

