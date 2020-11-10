package com.example.shared.service.response;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

/**
 * A response for a {@link com.example.shared.service.request.LoginRequest}.
 */
public class LoginResponse extends Response {

    public User user;

    public AuthToken authToken;

    public LoginResponse(String message) {
        super(false, message);
    }

    public LoginResponse(User user, AuthToken authToken) {
        super(true, null);
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
