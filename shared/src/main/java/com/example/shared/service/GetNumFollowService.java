package com.example.shared.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import java.io.IOException;

public interface GetNumFollowService {

    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) throws IOException, TweeterRemoteException;

}
