package com.example.server.service;

import com.example.server.dao.LoginDAO;
import com.example.shared.service.LoginService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse login(LoginRequest request) {
        return getLoginDAO().login(request);
    }

    public LoginDAO getLoginDAO() {
        return new LoginDAO();
    }
}
