package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FeedDAO;
import com.example.server.dao.FollowDAO;
import com.example.server.dao.GetFollowingDAO;
import com.example.server.dao.S3DAO;
import com.example.server.dao.StoryDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;
import com.example.shared.service.GetFollowingService;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

import java.util.ArrayList;
import java.util.List;


public class GetFollowingServiceImpl implements GetFollowingService {

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) {

        //Get Aliases of following
        List<String> followingAliases = getFollowDAO().getFollowing(request.getLoggedInUser(), request.getLastFollowing(), request.getLimit());

        List<User> users = new ArrayList<>();

        for (String currFollowing : followingAliases) {
            users.add(getUserDAO().getUser(currFollowing));
        }

        return new GetFollowingResponse(users, true); //Change for the future
    }

    public GetFollowingDAO getFollowingDAO() {
        return new GetFollowingDAO();
    }
}
