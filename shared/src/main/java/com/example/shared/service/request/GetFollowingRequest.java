package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class GetFollowingRequest extends DataRetrievalRequest{

    private final User lastFollowee;

    public GetFollowingRequest(User user, AuthToken authToken, int limit, User lastFollowee, int fragmentCode) {
        super(user, authToken, limit, fragmentCode);

        this.lastFollowee = lastFollowee;
    }

    public User getLastFollowing() {
        return lastFollowee;
    }
}
