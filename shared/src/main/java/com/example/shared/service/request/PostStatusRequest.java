package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;

public class PostStatusRequest {

    public Status status;
    public User user;

    public AuthToken authToken;

    public PostStatusRequest(Status status, User user, AuthToken authToken) {
        this.status = status;
        this.user = user;
        this.authToken = authToken;
    }

    public PostStatusRequest() {
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

}
