package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;
import com.example.shared.service.RegisterService;

import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class RegisterServiceProxy implements RegisterService {

    private static final String URL_PATH = "/register";

    @Override
    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        RegisterResponse registerResponse = serverFacade.register(request, URL_PATH);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }

        return registerResponse;
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
