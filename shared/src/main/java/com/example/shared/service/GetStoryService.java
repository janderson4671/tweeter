package com.example.shared.service;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import java.io.IOException;

public interface GetStoryService {

    public GetStoryResponse getStatuses(GetStoryRequest request);

}
