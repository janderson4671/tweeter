package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class GetFollowersRequest {

    private final User loggedInUser;

    private final AuthToken authToken;

    private final int limit;
    private final User lastFollower;

    public GetFollowersRequest(User loggedInUser, AuthToken authToken, int limit, User lastFollower) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastFollower = lastFollower;
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

    public User getLastFollower() {
        return lastFollower;
    }


}
