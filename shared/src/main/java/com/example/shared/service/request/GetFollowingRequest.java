package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class GetFollowingRequest {

    private String loggedInUser;
    private AuthToken authToken;
    private int limit;
    private String lastFollowing;

    public GetFollowingRequest(String loggedInUser, AuthToken authToken, int limit, String lastFollowing) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastFollowing = lastFollowing;
    }

    public GetFollowingRequest() {
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollowing(String lastFollowing) {
        this.lastFollowing = lastFollowing;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollowing() {
        return lastFollowing;
    }
}
