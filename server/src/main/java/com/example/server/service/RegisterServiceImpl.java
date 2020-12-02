package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.RegisterDAO;
import com.example.server.dao.S3DAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.RegisterService;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public RegisterResponse register(RegisterRequest request) {

        //Time to register :)

        //First we need to upload the image from the request object to AWS S3
        String profileURL = S3DAO.uploadProfileImage(request.getProfile(), request.getUsername());

        //Then we need to add this user to the database for valid users
        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(), profileURL, 0, 0);

        //Then we need to hash their password
        String password = hashPassword(request.getPassword());

        try {
            UserDAO.addUser(user, password);

            //Create a session for this user (Using the authToken table)
            AuthToken authToken = new AuthToken();
            AuthTokenDAO.createSession(user.getAlias(), authToken);

            return new RegisterResponse(user, authToken);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new RegisterResponse("Failed to Register");
        }

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

    public RegisterDAO getRegisterDAO() {
        return new RegisterDAO();
    }
}
