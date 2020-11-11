package com.example.shared.service.response;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class RegisterResponse extends Response {
    private User user;

    private AuthToken authToken;
    //Constructors

    public RegisterResponse(String message) {
        super(false, message);
    }
    public RegisterResponse(User user, AuthToken authToken) {
        super(true, null);
        this.user = user;
        this.authToken = authToken;
    }

    //Getter

    public User getUser() {
        return user;
    }
    public AuthToken getAuthToken() { return authToken; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

}
