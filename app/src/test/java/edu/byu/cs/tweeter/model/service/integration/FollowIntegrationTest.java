package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.FollowServiceProxy;

public class FollowIntegrationTest {

    FollowRequest validRequest;
    FollowServiceProxy service;

    FollowResponse response;

    User loggedInUser;
    User userToFollow;

    @BeforeEach
    public void setup() {

        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);
        userToFollow = new User("Follow", "User", "@FollowUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new FollowRequest(loggedInUser.getAlias(), new AuthToken(), userToFollow.getAlias(), false);

        service = new FollowServiceProxy();
    }

    @Test
    public void test_validRequest() {

        try {
            response = service.follow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isFollowing());

        validRequest = new FollowRequest(loggedInUser.getAlias(), new AuthToken(), userToFollow.getAlias(), false);

        try {
            response = service.follow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isFollowing());

    }

}
