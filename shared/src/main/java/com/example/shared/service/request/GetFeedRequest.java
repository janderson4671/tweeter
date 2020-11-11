package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class GetFeedRequest {

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    private String loggedInUser;

    private AuthToken authToken;

    private int limit;
    private Status lastStatus;


    public GetFeedRequest(String loggedInUser, AuthToken authToken, int limit, Status lastStatus) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public GetFeedRequest() {}

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

    @Override
    public String toString() {
        return "GetFeedRequest{" +
                "loggedInUser='" + loggedInUser + '\'' +
                ", authToken=" + authToken +
                ", limit=" + limit +
                ", lastStatus=" + lastStatus +
                '}';
    }
}
