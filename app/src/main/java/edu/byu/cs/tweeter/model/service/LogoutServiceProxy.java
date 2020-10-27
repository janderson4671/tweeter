package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceProxy implements LogoutService {

    private static final String URL_PATH = "/logout";

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        LogoutResponse response = getServerFacade().logout(request, URL_PATH);
        return response;
    }

    public ServerFacade getServerFacade() { return new ServerFacade(); }
}
