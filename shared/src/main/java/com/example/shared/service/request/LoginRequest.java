package com.example.shared.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class LoginRequest {

    public String username;
    public String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
