package com.example.server.service;

import com.example.server.dao.GetFeedDAO;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFeedService;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

import java.io.IOException;

public class GetFeedServiceImpl implements GetFeedService {
    @Override
    public GetFeedResponse getStatuses(GetFeedRequest request) throws IOException, TweeterRemoteException {
        return getFeedDAO().getStatuses(request);
    }

    GetFeedDAO getFeedDAO() {
        return new GetFeedDAO();
    }
}
