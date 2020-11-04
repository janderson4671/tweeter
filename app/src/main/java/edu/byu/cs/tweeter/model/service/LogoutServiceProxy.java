package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;
import com.example.shared.service.LogoutService;

public class LogoutServiceProxy implements LogoutService {

    private static final String URL_PATH = "/logout";

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        LogoutResponse response = getServerFacade().logout(request, URL_PATH);
        return response;
    }

    public ServerFacade getServerFacade() { return new ServerFacade(); }
}
