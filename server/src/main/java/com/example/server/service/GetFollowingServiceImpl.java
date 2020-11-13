package com.example.server.service;

import com.example.server.dao.GetFollowingDAO;
import com.example.shared.service.GetFollowingService;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;


public class GetFollowingServiceImpl implements GetFollowingService {
    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) {
        return getFollowingDAO().getFollowing(request);
    }

    public GetFollowingDAO getFollowingDAO() {
        return new GetFollowingDAO();
    }
}
