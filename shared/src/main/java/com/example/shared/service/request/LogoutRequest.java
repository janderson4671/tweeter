package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class LogoutRequest {

    public User user;
    public AuthToken authToken;

    public LogoutRequest(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public LogoutRequest() {
    }

    public User getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
