package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class FollowRequest {

    public String currUser;
    public AuthToken authToken;

    public String userToFollow;
    public boolean follow;

    //Constructor
    public FollowRequest(String currUser, AuthToken authToken, String userToFollow, boolean follow) {
        this.currUser = currUser;
        this.authToken = authToken;
        this.userToFollow = userToFollow;
        this.follow = follow;
    }

    public FollowRequest() {}

    //Getters
    public String getCurrUser() {
        return currUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getUserToFollow() {
        return userToFollow;
    }

    public boolean isFollow() {
        return follow;
    }

}
