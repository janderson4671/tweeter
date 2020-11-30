package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.model.service.GetFollowersServiceProxy;

public class GetFollowersIntegrationTest {

    GetFollowersRequest validRequest;
    GetFollowersResponse response;
    User loggedInUser;

    GetFollowersServiceProxy service;

    @BeforeEach
    public void setup() {
        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new GetFollowersRequest(loggedInUser.getAlias(), new AuthToken(), 12, null);

        service = new GetFollowersServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.getFollowers(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);

        List<User> followers = response.getUsers();

        Assertions.assertNotNull(followers.size());

        if (followers.size() > 0) {
            for (User currUser : followers) {
                Assertions.assertNotNull(currUser.getAlias());
            }
        }

        validRequest = new GetFollowersRequest(loggedInUser.getAlias(), new AuthToken(), 12, followers.get(followers.size() - 1).getAlias());

        try {
            response = service.getFollowers(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);

        followers = response.getUsers();

        Assertions.assertNotNull(followers.size());

        if (followers.size() > 0) {
            for (User currUser : followers) {
                Assertions.assertNotNull(currUser.getAlias());
            }
        }

    }

}
