package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;

public class PostStatusRequest {

    public Status status;
    public String user;

    public AuthToken authToken;

    public PostStatusRequest(Status status, String user, AuthToken authToken) {
        this.status = status;
        this.user = user;
        this.authToken = authToken;
    }

    public PostStatusRequest() {
    }

    public Status getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

}
