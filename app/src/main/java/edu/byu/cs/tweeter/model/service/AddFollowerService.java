package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.Response;

public class AddFollowerService {

    public AddFollowerResponse addFollower(AddFollowerRequest request) throws IOException {
        AddFollowerResponse response = getServerFacade().addFollower(request);
        return response;
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
