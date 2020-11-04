package com.example.server.dao;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

public class LoginDAO {
    public LoginResponse login(LoginRequest request) {

        //Using dummy data for now

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        return new LoginResponse(user, new AuthToken());
    }
}
