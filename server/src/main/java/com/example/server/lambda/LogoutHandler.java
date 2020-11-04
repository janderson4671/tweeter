package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {


    public LogoutResponse handleRequest(LogoutRequest request, Context context) {

        LogoutServiceImpl service = new LogoutServiceImpl();
        return service.logout(request);

    }

}
