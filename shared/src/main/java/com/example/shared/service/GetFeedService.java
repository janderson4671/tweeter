package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

public interface GetFeedService {

    public GetFeedResponse getStatuses(GetFeedRequest request);

}
