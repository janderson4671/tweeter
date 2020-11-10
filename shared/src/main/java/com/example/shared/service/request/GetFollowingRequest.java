package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class GetFollowingRequest {

    public User loggedInUser;

    public AuthToken authToken;
    public int limit;
    public User lastFollowing;

    public GetFollowingRequest(User loggedInUser, AuthToken authToken, int limit, User lastFollowing) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastFollowing = lastFollowing;
    }

    public GetFollowingRequest() {
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowing() {
        return lastFollowing;
    }
}
