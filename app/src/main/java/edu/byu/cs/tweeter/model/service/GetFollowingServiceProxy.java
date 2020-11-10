package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import java.net.URL;

import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;
import com.example.shared.service.GetFollowingService;

import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetFollowingServiceProxy implements GetFollowingService {

    static final String URL_PATH_FOLLOWING = "/getfollowing";

    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException {

        GetFollowingResponse response = getServerFacade().getFollowing(request, URL_PATH_FOLLOWING);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(GetFollowingResponse response) throws IOException {
        for(User user : response.getUsers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            //user.setImageBytes(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
