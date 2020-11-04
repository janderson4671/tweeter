package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.LoginServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

    public LoginResponse handleRequest(LoginRequest request, Context context) {

        LoginServiceImpl service = new LoginServiceImpl();
        return service.login(request);

    }

}
