package com.example.shared.service.response;

public class DoesFollowResponse extends Response {

    private boolean doesFollow;

    public DoesFollowResponse(boolean doesFollow) {
        super(true);
        this.doesFollow = doesFollow;
    }

    public DoesFollowResponse(boolean success, String message) {
        super(success, message);
    }

    public boolean isDoesFollow() {
        return doesFollow;
    }

    public void setDoesFollow(boolean doesFollow) {
        this.doesFollow = doesFollow;
    }

}
