package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class FollowRequest {

    private String currUser;
    private AuthToken authToken;
    private String userToFollow;
    private boolean follow;

    //Constructors
    public FollowRequest() {}

    public FollowRequest(String currUser, AuthToken authToken, String userToFollow, boolean follow) {
        this.currUser = currUser;
        this.authToken = authToken;
        this.userToFollow = userToFollow;
        this.follow = follow;
    }

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

    //Setters
    public void setCurrUser(String currUser) {
        this.currUser = currUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setUserToFollow(String userToFollow) {
        this.userToFollow = userToFollow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }
}
