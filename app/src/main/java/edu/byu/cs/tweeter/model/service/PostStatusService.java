package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusService {

    public PostStatusResponse addPost(PostStatusRequest request) {
        PostStatusResponse response = getServerFacade().postStatus(request);
        return response;
    }

    private ServerFacade getServerFacade() { return new ServerFacade(); }
}
