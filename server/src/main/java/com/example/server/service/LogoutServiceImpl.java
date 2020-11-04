package com.example.server.service;

import com.example.server.dao.LogoutDAO;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {
        return getLogoutDAO().logout(request);
    }

    public LogoutDAO getLogoutDAO() {
        return new LogoutDAO();
    }
}
