package com.example.server.dao;

import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowDAO {

    public FollowResponse follow(FollowRequest request) {

        //Using dummy data for now
        return new FollowResponse(true, "Following Another User");
    }
}
