package com.devopscat.springproject.service;

import com.devopscat.springproject.entity.User;
import com.devopscat.springproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username + "유저가 존재하지 않습니다.");
        } else {

        }
        return null;
    }
}
