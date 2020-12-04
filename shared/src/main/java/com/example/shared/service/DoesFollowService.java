package com.example.shared.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

import java.io.IOException;

public interface DoesFollowService {

    public DoesFollowResponse doesFollow(DoesFollowRequest request) throws IOException, TweeterRemoteException;

}
