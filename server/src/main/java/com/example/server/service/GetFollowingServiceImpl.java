package com.example.server.service;

import com.example.server.dao.FollowDAO;
import com.example.server.dao.GetFollowingDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;
import com.example.shared.service.GetFollowingService;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

import java.util.ArrayList;
import java.util.List;


public class GetFollowingServiceImpl implements GetFollowingService {
    @Override
    public GetFollowingResponse getFollowing(GetFollowingRequest request) {

//        if (!AuthTokenDAO.validateUser(request.getCurrUser())) {
//            return //BAD RESPONSE
//        }

        //Get Aliases of following
//        List<String> followingAliases = FollowDAO.getFollowing(request.getLoggedInUser(), request.getLastFollowing());
//
//        List<User> users = new ArrayList<>();
//
//        for (String currFollowing : followingAliases) {
//            users.add(UserDAO.getUser(currFollowing));
//        }
//
//        return new GetFollowingResponse(users, true); //Change for the future

        return getFollowingDAO().getFollowing(request);
    }

    public GetFollowingDAO getFollowingDAO() {
        return new GetFollowingDAO();
    }
}
