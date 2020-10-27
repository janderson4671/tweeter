package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowService {

    public FollowResponse addFollower(FollowRequest request) throws IOException {
        FollowResponse response = getServerFacade().addFollower(request);
        return response;
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
