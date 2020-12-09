package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;
import com.example.shared.service.LoginService;

import com.example.shared.util.ByteArrayUtils;

public class LoginServiceProxy implements LoginService {

    private static final String URL_Path = "/login";

    @Override
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginResponse loginResponse = serverFacade.login(request, URL_Path);

        if(loginResponse.isSuccess()) {
            loadImage(loginResponse.getUser());
        }

        return loginResponse;
    }

    private void loadImage(User user) throws IOException {

        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
