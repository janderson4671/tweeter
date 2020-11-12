package edu.byu.cs.tweeter.model.service;

import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetFollowersServiceProxy implements GetFollowersService {

    static final String URL_PATH_FOLLOWER = "/getfollowers";

    private void loadImages(GetFollowersResponse response) throws IOException {
        for(User user : response.getUsers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

    @Override
    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws IOException, TweeterRemoteException {
        GetFollowersResponse response = getServerFacade().getFollowers(request, URL_PATH_FOLLOWER);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }
}
