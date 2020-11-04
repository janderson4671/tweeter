package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;
import com.example.shared.service.PostStatusService;

public class PostStatusServiceProxy implements PostStatusService {

    private static final String URL_PATH = "/poststatus";

    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        PostStatusResponse response = getServerFacade().postStatus(request, URL_PATH);
        return response;
    }

    public ServerFacade getServerFacade() { return new ServerFacade(); }
}
