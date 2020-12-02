package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.LoginDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.LoginService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse login(LoginRequest request) {

        //Start the session
        AuthToken authToken = new AuthToken();
        AuthTokenDAO.createSession(request.getUsername(), authToken);

        //Grab the user
        User user = UserDAO.getUser(request.getUsername());

        return new LoginResponse(user, authToken);
    }

    public LoginDAO getLoginDAO() {
        return new LoginDAO();
    }
}
