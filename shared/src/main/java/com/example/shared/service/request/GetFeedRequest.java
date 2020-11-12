package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class GetFeedRequest {

    private String loggedInUser;
    private AuthToken authToken;
    private int limit;
    private Status lastStatus;

    //Constructors
    public GetFeedRequest() {}

    public GetFeedRequest(String loggedInUser, AuthToken authToken, int limit, Status lastStatus) {
        this.loggedInUser = loggedInUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastStatus = lastStatus;
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

    public Status getLastStatus() {
        return lastStatus;
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

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

}
