package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FollowDAO;
import com.example.shared.service.DoesFollowService;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

public class DoesFollowServiceImpl implements DoesFollowService {
    @Override
    public DoesFollowResponse doesFollow(DoesFollowRequest request) {

        //Authenticate the user
        if (!AuthTokenDAO.validateUser(request.getAuthToken())) {
            throw new RuntimeException("User Session Timed Out");
        }

        boolean doesFollow = FollowDAO.doesFollow(request.getLoggedInUser(), request.getUserToCheck());

        return new DoesFollowResponse(doesFollow);
    }
}
