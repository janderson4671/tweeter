package com.example.server.service;

import com.example.server.dao.GetNumFollowDAO;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

public class GetNumFollowServiceImpl implements GetNumFollowService {
    @Override
    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) {
        return getNumFollowDAO().getNumFollow(request);
    }

    public GetNumFollowDAO getNumFollowDAO() {
        return new GetNumFollowDAO();
    }
}
