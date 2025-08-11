package com.devopscat.springproject.mapper;

import com.devopscat.springproject.entity.User;
import com.devopscat.springproject.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
// 자바와 mysql 연결
public interface UserMapper {
    int insertUser(User user);
    String selectUsername(String username);
    String selectWriter(String writer);
}
