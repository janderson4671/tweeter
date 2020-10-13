package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutService {

    public LogoutResponse logout(LogoutRequest request) throws IOException {
        LogoutResponse response = getServerFacade().logout(request);
        return response;
    }

    public ServerFacade getServerFacade() { return new ServerFacade(); }
}
