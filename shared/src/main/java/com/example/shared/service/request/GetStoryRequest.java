package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class GetStoryRequest {

    public String loggedInUser;

    public AuthToken authToken;

    public int limit;
    public Status lastStatus;

    public GetStoryRequest(String loggedInUser, AuthToken authToken, int limit, Status lastStatus) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public GetStoryRequest() {
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

    public Status getLastStatus() {
        return lastStatus;
    }
}
