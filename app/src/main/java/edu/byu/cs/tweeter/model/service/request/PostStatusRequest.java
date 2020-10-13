package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

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
