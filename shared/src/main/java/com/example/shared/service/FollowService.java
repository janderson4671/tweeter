package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public interface FollowService {

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException;

}
