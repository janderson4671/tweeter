package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;

public class DoesFollowRequest {

    private AuthToken authToken;
    private String loggedInUser;
    private String userToCheck;

    public DoesFollowRequest() {}

    public DoesFollowRequest(AuthToken authToken, String loggedInUser, String userToCheck) {
        this.authToken = authToken;
        this.loggedInUser = loggedInUser;
        this.userToCheck = userToCheck;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getUserToCheck() {
        return userToCheck;
    }

    public void setUserToCheck(String userToCheck) {
        this.userToCheck = userToCheck;
    }

}
