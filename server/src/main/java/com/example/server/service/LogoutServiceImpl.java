package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.LogoutDAO;
import com.example.shared.domain.AuthToken;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {

        //Remove the session from the authToken Table
//        AuthTokenDAO.destroySession(request.getUser());
//
//        return new LogoutResponse(true, "Successfully Logged Out!");

        return getLogoutDAO().logout(request);
    }

    public LogoutDAO getLogoutDAO() {
        return new LogoutDAO();
    }
}
