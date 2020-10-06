package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.DataRetrievalRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.DataRetrievalResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class DataRetrievalService {

    private final int FOLLOWING_FRAG = 2;
    private final int FOLLOWER_FRAG = 3;

    public DataRetrievalResponse getData(DataRetrievalRequest request) throws IOException {

        if (request.getFragmentCode() == FOLLOWING_FRAG) {
            return getFolloweesService(request);
        } else if (request.getFragmentCode() == FOLLOWER_FRAG) {
            return getFollowersService(request);
        }

        return null;
    }

    private DataRetrievalResponse getFolloweesService(DataRetrievalRequest dataRequest) throws IOException{
        FollowRequest request = new FollowRequest(dataRequest.getUser(), dataRequest.getLimit(),
                (User) dataRequest.getLastElement());

        FollowResponse response = getServerFacade().getFollowees(request);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return new DataRetrievalResponse(response.getFollowees(), response.getHasMorePages());
    }

    private DataRetrievalResponse getFollowersService(DataRetrievalRequest dataRequest) throws IOException {
        FollowRequest request = new FollowRequest(dataRequest.getUser(), dataRequest.getLimit(),
                (User) dataRequest.getLastElement());

        FollowResponse response = getServerFacade().getFollowers(request);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return new DataRetrievalResponse(response.getFollowees(), response.getHasMorePages());
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(FollowResponse response) throws IOException {
        for(User user : response.getFollowees()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
