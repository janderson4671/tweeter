package com.example.server.service;

import com.example.server.dao.FollowDAO;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.FollowService;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

import java.io.IOException;

public class FollowServiceImpl implements FollowService {
    @Override
    public FollowResponse follow(FollowRequest request) {
        return getFollowDAO().follow(request);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
