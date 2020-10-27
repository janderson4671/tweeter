package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowRequest {

    private User currUser;
    private AuthToken authToken;

    private User userToFollow;
    private boolean follow;

    //Constructor
    public FollowRequest(User currUser, AuthToken authToken, User userToFollow, boolean follow) {
        this.currUser = currUser;
        this.authToken = authToken;
        this.userToFollow = userToFollow;
        this.follow = follow;
    }

    //Getters
    public User getCurrUser() {
        return currUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public User getUserToFollow() {
        return userToFollow;
    }

    public boolean isFollow() {
        return follow;
    }

}
