package edu.byu.cs.tweeter.model.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.DoesFollowService;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

public class DoesFollowServiceProxy implements DoesFollowService {

    public static final String URL_PATH_DOES_FOLLOW = "/doesfollow";

    @Override
    public DoesFollowResponse doesFollow(DoesFollowRequest request) throws IOException, TweeterRemoteException {
        DoesFollowResponse response = getServerFacade().doesFollow(request, URL_PATH_DOES_FOLLOW);
        return response;
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
