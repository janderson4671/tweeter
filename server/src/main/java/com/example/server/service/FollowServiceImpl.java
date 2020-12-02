package com.example.server.service;

import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.FollowDAO;
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
//        if (!AuthTokenDAO.validateUser(request.getCurrUser())) {
//            return //BAD RESPONSE
//        }

        //Check whether this user wants to follow or unfollow the other user
//        if (request.isFollow()) {
//            FollowDAO.followUser(request.getCurrUser(), request.getUserToFollow());
//        } else {
//            FollowDAO.unFollowUser(request.getCurrUser(), request.getUserToFollow());
//        }


        return getFollowDAO().follow(request);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
