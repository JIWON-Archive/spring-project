package com.devopscat.springproject.controller;

import com.devopscat.springproject.entity.Role;
import com.devopscat.springproject.entity.User;
import com.devopscat.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@ModelAttribute User user, ModelMap model) {
        String userPassword = user.getPassword();
        System.out.println("userPassword:" + userPassword);
        user.setRole(Role.MEMBER);
        String passwordEncoded = passwordEncoder.encode(userPassword);
        user.setPassword(passwordEncoded);
        userService.insertUser(user);
        return "redirect:loginPage";
    }
}


