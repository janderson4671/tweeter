package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;
import com.example.shared.service.FollowService;

public class FollowServiceProxy implements FollowService {

    private static final String URL_PATH_FOLLOW = "/follow";
    private static final String URL_PATH_UNFOLLOW = "/unfollow";

    @Override
    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {

        String url = null;

        if (request.isFollow()) {
            url = URL_PATH_FOLLOW;
        } else {
            url = URL_PATH_UNFOLLOW;
        }

        FollowResponse response = getServerFacade().follow(request, url);
        return response;
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
