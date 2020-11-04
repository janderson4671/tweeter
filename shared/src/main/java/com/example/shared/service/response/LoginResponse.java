package com.example.shared.service.response;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

/**
 * A response for a {@link com.example.shared.service.request.LoginRequest}.
 */
public class LoginResponse extends Response {

    private User user;
    private AuthToken authToken;

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
}