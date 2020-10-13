package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class AddFollowerService {

    public Response addFollower(AddFollowerRequest request) {
        Response response = getServerFacade().addFollower(request);
        return response;
    }

    private ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
