package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {

        //Remove the session from the authToken Table
        getAuthTokenDAO().destroySession(request.getAuthToken());

        return new LogoutResponse(true, "Successfully Logged Out!");
    }

}
