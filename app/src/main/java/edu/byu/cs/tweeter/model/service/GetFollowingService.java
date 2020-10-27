package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public interface GetFollowingService {

    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException;

}
