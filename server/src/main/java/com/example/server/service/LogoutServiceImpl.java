package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FeedDAO;
import com.example.server.dao.FollowDAO;
import com.example.server.dao.LogoutDAO;
import com.example.server.dao.S3DAO;
import com.example.server.dao.StoryDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.AuthToken;
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

    public LogoutDAO getLogoutDAO() {
        return new LogoutDAO();
    }
}
