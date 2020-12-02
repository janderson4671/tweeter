package com.example.server.ServerModel;

import com.example.shared.domain.User;

public class DBUser {

    private User user;
    private String password;

    public DBUser(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
