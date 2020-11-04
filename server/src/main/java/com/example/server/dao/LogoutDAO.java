package com.example.server.dao;

import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutDAO {

    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse(true, "Successfully Logged Out!");
    }

}
