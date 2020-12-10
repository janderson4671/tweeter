package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FollowDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.service.FollowService;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowServiceImpl implements FollowService {

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public FollowResponse follow(FollowRequest request) {

        UserDAO userDAO = getUserDAO();

        //Authenticate the user with authtoken
        if (!getAuthTokenDAO().validateUser(request.getAuthToken())) {
            return new FollowResponse(false, "User Session Timed Out");
        }

        boolean success;
        boolean isFollowing;

        //Check whether this user wants to follow or unfollow the other user
        if (request.isFollow()) {
            success = getFollowDAO().follow(request.getCurrUser(), request.getUserToFollow());
            isFollowing = true;

            //update the counts
            userDAO.updateFollowing(request.getCurrUser(), true);
            userDAO.updateFollower(request.getUserToFollow(), true);

        } else {
            success = getFollowDAO().unFollow(request.getCurrUser(), request.getUserToFollow());
            isFollowing = false;

            //update the counts
            userDAO.updateFollowing(request.getCurrUser(), false);
            userDAO.updateFollower(request.getUserToFollow(), false);
        }

        return new FollowResponse(success, "Finished", isFollowing);
    }
}
