package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public interface GetFollowingService {

    public GetFollowingResponse getFollowing(GetFollowingRequest request);

}
