package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class LogoutRequest {

    private String user;

    private AuthToken authToken;
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

    public void setUser(String user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
