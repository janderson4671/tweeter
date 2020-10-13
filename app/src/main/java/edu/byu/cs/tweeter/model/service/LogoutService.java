package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutService {

    public LogoutResponse logout(LogoutRequest request) {
        LogoutResponse response = getServerFacade().logout(request);
        return response;
    }

    private ServerFacade getServerFacade() { return new ServerFacade(); }
}
