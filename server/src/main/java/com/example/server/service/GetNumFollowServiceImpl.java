package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FeedDAO;
import com.example.server.dao.FollowDAO;
import com.example.server.dao.GetNumFollowDAO;
import com.example.server.dao.S3DAO;
import com.example.server.dao.StoryDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

public class GetNumFollowServiceImpl implements GetNumFollowService {

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) {

        //Get the current user object
        User user = getUserDAO().getUser(request.getUser());

        if (user == null) {
            return new GetNumFollowResponse(false, "User Not Found");
        }

        return new GetNumFollowResponse(user.getNumFollowers(), user.getNumFollowing());
    }

    public GetNumFollowDAO getNumFollowDAO() {
        return new GetNumFollowDAO();
    }
}
