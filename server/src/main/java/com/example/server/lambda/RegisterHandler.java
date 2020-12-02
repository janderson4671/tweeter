package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.RegisterServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.RegisterService;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

import java.util.logging.Logger;

public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    public RegisterResponse handleRequest(RegisterRequest request, Context context) {

        RegisterServiceImpl service = new RegisterServiceImpl();

        RegisterResponse response = service.register(request);

        System.err.println("Checking Response");
        System.err.println(response.getUser().getAlias());
        System.err.println(response.getUser().getImageUrl());

        return response;
    }



}
