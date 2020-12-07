package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class GetStoryRequest {

    public String currUser;
    private AuthToken authToken;
    private int limit;
    private Status lastStatus;

    //Constructors
    public GetStoryRequest() {}

    public GetStoryRequest(String currUser, AuthToken authToken, int limit, Status lastStatus) {
        this.currUser = currUser;
        this.authToken = authToken;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    //Getters
    public String getUser() {
        return currUser;
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
    public void setUser(String currUser) {
        this.currUser = currUser;
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
