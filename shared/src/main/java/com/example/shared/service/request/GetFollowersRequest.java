package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class GetFollowersRequest {

    public String loggedInUser;

    public AuthToken authToken;

    public int limit;
    public String lastFollower;

    public GetFollowersRequest(String loggedInUser, AuthToken authToken, int limit, String lastFollower) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public GetFollowersRequest() {
    }

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


}
