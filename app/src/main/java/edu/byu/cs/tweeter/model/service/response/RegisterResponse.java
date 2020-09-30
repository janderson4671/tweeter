package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse extends Response {

    User user;
    AuthToken authToken;

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

}
