package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FollowDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.FollowService;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

import java.io.IOException;
import java.net.Authenticator;

public class FollowServiceImpl implements FollowService {
    @Override
    public FollowResponse follow(FollowRequest request) {

        //Authenticate the user with authtoken
        if (!AuthTokenDAO.validateUser(request.getAuthToken())) {
            return new FollowResponse(false, "User Session Timed Out", request.isFollow());
        }

        boolean success;
        boolean isFollowing;

        //Check whether this user wants to follow or unfollow the other user
        if (request.isFollow()) {
            success = FollowDAO.follow(request.getCurrUser(), request.getUserToFollow());
            isFollowing = true;

            //update the counts
            UserDAO.updateFollowing(request.getCurrUser(), true);
            UserDAO.updateFollower(request.getUserToFollow(), true);

        } else {
            success = FollowDAO.unFollow(request.getCurrUser(), request.getUserToFollow());
            isFollowing = false;

            //update the counts
            UserDAO.updateFollowing(request.getCurrUser(), false);
            UserDAO.updateFollower(request.getUserToFollow(), false);
        }

        return new FollowResponse(success, "Finished", isFollowing);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
