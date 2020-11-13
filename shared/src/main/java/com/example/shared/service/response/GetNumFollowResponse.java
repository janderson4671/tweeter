package com.example.shared.service.response;

public class GetNumFollowResponse extends Response {

    private int numFollowers;
    private int numFollowing;

    //Constructors
    public GetNumFollowResponse() {
        super(true, "Got the numbers");
    }

    public GetNumFollowResponse(boolean success, String message) {
        super(success, message);
    }

    public GetNumFollowResponse(int numFollowers, int numFollowing) {

        super(true, "Got the numbers");

        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
    }

    //Getters
    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    //Setters
    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }
}
