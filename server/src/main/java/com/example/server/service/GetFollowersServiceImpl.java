package com.example.server.service;

import com.example.server.dao.GetFollowersDAO;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

public class GetFollowersServiceImpl implements GetFollowersService {
    @Override
    public GetFollowersResponse getFollowers(GetFollowersRequest request) {
        return getFollowersDAO().getFollowers(request);
    }

    GetFollowersDAO getFollowersDAO() {
        return new GetFollowersDAO();
    }
}
