package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    public RegisterResponse handleRequest(RegisterRequest request, Context context) {

        //Using dummy data for now

        User user = new User(request.getFirstName(), request.getLastName(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        return new RegisterResponse(user, new AuthToken());
    }

}
