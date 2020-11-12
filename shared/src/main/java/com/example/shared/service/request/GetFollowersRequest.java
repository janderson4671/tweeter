package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class GetFollowersRequest {

    private String loggedInUser;
    private AuthToken authToken;
    private int limit;
    private String lastFollower;

    //Constructors
    public GetFollowersRequest() {}

    public GetFollowersRequest(String loggedInUser, AuthToken authToken, int limit, String lastFollower) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    //Getters
    public String getLoggedInUser() {
        return loggedInUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollower() {
        return lastFollower;
    }

    //Setters
    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }

}
