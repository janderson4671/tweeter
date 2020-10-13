package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowRequest extends DataRetrievalRequest{

    private final User lastFollowee;

    public FollowRequest(User user, AuthToken authToken, int limit, User lastFollowee, int fragmentCode) {
        super(user, authToken, limit, fragmentCode);

        this.lastFollowee = lastFollowee;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
}
