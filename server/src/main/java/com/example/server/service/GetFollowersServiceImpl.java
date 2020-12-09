package com.example.server.service;

import com.example.server.dao.FollowDAO;
import com.example.server.dao.GetFollowersDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class GetFollowersServiceImpl implements GetFollowersService {

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public GetFollowersResponse getFollowers(GetFollowersRequest request) {

        //Return all the aliases for
        List<String> followersAliases = getFollowDAO().getUsersThatFollow(request.getLoggedInUser(), request.getLastFollower(),request.getLimit());

        List<User> users = new ArrayList<>();

        for (String currFollower : followersAliases) {
            users.add(getUserDAO().getUser(currFollower));
        }

        return new GetFollowersResponse(users, true); //TODO: Get Has more pages
    }

    public GetFollowersDAO getFollowersDAO() {
        return new GetFollowersDAO();
    }
}
