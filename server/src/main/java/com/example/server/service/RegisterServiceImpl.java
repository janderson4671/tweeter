package com.example.server.service;

import com.example.server.dao.RegisterDAO;
import com.example.shared.service.RegisterService;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public RegisterResponse register(RegisterRequest request) {
        return getRegisterDAO().register(request);
    }

    public RegisterDAO getRegisterDAO() {
        return new RegisterDAO();
    }
}
