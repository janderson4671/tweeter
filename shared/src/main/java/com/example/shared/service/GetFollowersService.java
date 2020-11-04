package com.example.shared.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import java.io.IOException;

public interface GetFollowersService {

    public GetFollowersResponse getFollowers(GetFollowersRequest request);

}
