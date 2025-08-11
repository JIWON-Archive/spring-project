package com.devopscat.springproject.entity;
// MVC MODEL 영역
public class User {
    private int idx;
    private String username;
    private String password;
    private String writer;
    private Role role;

    public User(){}

    // 인텔리제이 자동완성 단축키 Alt + Insert
    public User(int idx, String username, String password, String writer, Role role) {
        this.idx = idx;
        this.username = username;
        this.password = password;
        this.writer = writer;
        this.role = role;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}