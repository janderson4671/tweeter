package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class GetFollowingRequest {

    public String loggedInUser;

    public AuthToken authToken;
    public int limit;
    public String lastFollowing;

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
