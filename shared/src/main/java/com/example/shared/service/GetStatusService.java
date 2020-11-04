package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetStatusRequest;
import com.example.shared.service.response.GetStatusResponse;

public interface GetStatusService {

    public GetStatusResponse getStatuses(GetStatusRequest request) throws IOException, TweeterRemoteException;

}
