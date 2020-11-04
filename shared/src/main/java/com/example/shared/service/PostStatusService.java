package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public interface PostStatusService {

    public PostStatusResponse postStatus(PostStatusRequest request);

}
