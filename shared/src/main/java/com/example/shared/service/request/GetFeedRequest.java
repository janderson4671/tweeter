package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;

public class GetFeedRequest {

    public User loggedInUser;

    public AuthToken authToken;

    public int limit;
    public Status lastStatus;


    public GetFeedRequest(User loggedInUser, AuthToken authToken, int limit, Status lastStatus) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public GetFeedRequest() {
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

    public Status getLastStatus() {
        return lastStatus;
    }
}
