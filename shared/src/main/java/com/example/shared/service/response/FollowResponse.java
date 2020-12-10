package com.example.shared.service.response;

import com.example.shared.service.request.FollowRequest;

public class FollowResponse extends Response {

    private boolean following;

    //Constructors
    public FollowResponse() {
        super(true, "Dummy Message");
    }

    public FollowResponse(boolean isSuccess, String message, boolean following) {
        super(isSuccess, message);

        this.following = following;
    }

    public FollowResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    //Getter
    public boolean isFollowing() {
        return following;
    }

    //Setter
    public void setFollowing(boolean following) {
        this.following = following;
    }

}
