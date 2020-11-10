package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class FollowRequest {

    public User currUser;
    public AuthToken authToken;

    public User userToFollow;
    public boolean follow;

    //Constructor
    public FollowRequest(User currUser, AuthToken authToken, User userToFollow, boolean follow) {
        this.currUser = currUser;
        this.authToken = authToken;
        this.userToFollow = userToFollow;
        this.follow = follow;
    }

    public FollowRequest() {}

    //Getters
    public User getCurrUser() {
        return currUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public User getUserToFollow() {
        return userToFollow;
    }

    public boolean isFollow() {
        return follow;
    }

}
