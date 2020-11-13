package edu.byu.cs.tweeter.model.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;

public class GetNumFollowServiceProxy implements GetNumFollowService {

    private static final String URL_PATH_GET_NUM_FOLLOW = "/getnumfollow";

    @Override
    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) throws IOException, TweeterRemoteException {

        GetNumFollowResponse response = getServerFacade().getNumFollow(request, URL_PATH_GET_NUM_FOLLOW);
        return response;

    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
