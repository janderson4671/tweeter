package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.GetNumFollowDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

public class GetNumFollowServiceImpl implements GetNumFollowService {
    @Override
    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) {

        if (!AuthTokenDAO.validateUser(request.getAuthToken())) {
            return new GetNumFollowResponse(false, "User Session Expired");
        }

        //Get the current user object
        User user = UserDAO.getUser(request.getUser());

        if (user == null) {
            return new GetNumFollowResponse(false, "User Not Found");
        }

        return new GetNumFollowResponse(user.getNumFollowers(), user.getNumFollowing());
    }

    public GetNumFollowDAO getNumFollowDAO() {
        return new GetNumFollowDAO();
    }
}
