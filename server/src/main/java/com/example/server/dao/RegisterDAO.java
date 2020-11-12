package com.example.server.dao;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterDAO {

    public RegisterResponse register(RegisterRequest request) {

        //Using dummy data for now


        User user = new User(request.getFirstName(), request.getLastName(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        return new RegisterResponse(user, new AuthToken());
    }

}
