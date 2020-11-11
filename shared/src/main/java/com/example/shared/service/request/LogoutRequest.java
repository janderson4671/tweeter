package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class LogoutRequest {

    public String user;
    public AuthToken authToken;

    public LogoutRequest(String user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public LogoutRequest() {
    }

    public String getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
