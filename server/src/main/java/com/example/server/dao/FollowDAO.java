package com.example.server.dao;

import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowDAO {

    public FollowResponse follow(FollowRequest request) {

        if (request.isFollow()) {
            //We need to unfollow them
            return new FollowResponse(true, "Unfollowing" + request.getUserToFollow(), false);
        } else {
            //We need to follow them
            return new FollowResponse(true, "Following" + request.getUserToFollow(), true);
        }

    }
}
