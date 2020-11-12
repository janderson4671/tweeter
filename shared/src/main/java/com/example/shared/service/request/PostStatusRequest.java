package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class PostStatusRequest {

    private Status status;
    private String user;
    private AuthToken authToken;

    //Constructors
    public PostStatusRequest() {}

    public PostStatusRequest(Status status, String user, AuthToken authToken) {
        this.status = status;
        this.user = user;
        this.authToken = authToken;
    }

    //Getters
    public Status getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    //Setters
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

}
