package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.LoginService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginServiceImpl implements LoginService {

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        //Validate their password
        String password = hashPassword(request.getPassword());

        if (!getUserDAO().correctPassword(request.getUsername(), password)) {
            //Incorrect Password
            return new LoginResponse("Incorrect Password");
        }

        //Start the session
        AuthToken authToken = new AuthToken();
        getAuthTokenDAO().createSession(authToken);

        //Grab the user
        User user = getUserDAO().getUser(request.getUsername());

        return new LoginResponse(user, authToken);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            byte [] messageDigest = digest.digest(password.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);

            String hashtext = number.toString();

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

}
