package com.example.shared.service.request;

import com.example.shared.domain.Status;
import com.example.shared.domain.User;

public class PostStatusRequest {

    Status status;
    User user;

    public PostStatusRequest(Status status, User user) {
        this.status = status;
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

}
