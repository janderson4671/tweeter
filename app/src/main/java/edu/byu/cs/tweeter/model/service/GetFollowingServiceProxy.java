package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetFollowingServiceProxy implements GetFollowingService {

    static final String URL_PATH_FOLLOWING = "/getfollowing";
    static final String URL_PATH_FOLLOWERS = "/getfollowers";

    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException {

        GetFollowingResponse response;

        if (request.getFragmentCode() == 2) {
            response = getServerFacade().getFollowing(request, URL_PATH_FOLLOWERS);
        } else {
            response = getServerFacade().getFollowing(request, URL_PATH_FOLLOWING);
        }

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(GetFollowingResponse response) throws IOException {
        for(User user : response.getFollowees()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
