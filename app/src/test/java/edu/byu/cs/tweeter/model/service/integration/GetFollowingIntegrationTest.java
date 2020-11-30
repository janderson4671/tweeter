package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.model.service.GetFollowingServiceProxy;

public class GetFollowingIntegrationTest {

    GetFollowingRequest validRequest;
    GetFollowingResponse response;
    User loggedInUser;

    GetFollowingServiceProxy service;

    @BeforeEach
    public void setup() {
        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new GetFollowingRequest(loggedInUser.getAlias(), new AuthToken(), 12, null);

        service = new GetFollowingServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.getFollowing(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);

        List<User> following = response.getUsers();

        Assertions.assertNotNull(following.size());

        if (following.size() > 0) {
            for (User currUser : following) {
                Assertions.assertNotNull(currUser.getAlias());
            }
        }

        validRequest = new GetFollowingRequest(loggedInUser.getAlias(), new AuthToken(), 12, following.get(following.size() - 1).getAlias());

        try {
            response = service.getFollowing(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);

        following = response.getUsers();

        Assertions.assertNotNull(following.size());

        if (following.size() > 0) {
            for (User currUser : following) {
                Assertions.assertNotNull(currUser.getAlias());
            }
        }


    }

}
