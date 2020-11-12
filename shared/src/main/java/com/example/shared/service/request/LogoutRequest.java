package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class LogoutRequest {

    private String user;
    private AuthToken authToken;

    //Constructors
    public LogoutRequest() {}

    public LogoutRequest(String user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    //Getters
    public String getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    //Setters
    public void setUser(String user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
