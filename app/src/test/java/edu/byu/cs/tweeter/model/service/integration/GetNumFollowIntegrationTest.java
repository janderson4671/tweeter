package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.GetNumFollowServiceProxy;

public class GetNumFollowIntegrationTest {

    GetNumFollowRequest validRequest;
    GetNumFollowServiceProxy service;

    GetNumFollowResponse response;

    User loggedInUser;

    @BeforeEach
    public void setup() {

        loggedInUser = new User("Fran", "Franklin", "@FranFranklin",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new GetNumFollowRequest(loggedInUser.getAlias(), new AuthToken());

        service = new GetNumFollowServiceProxy();
    }

    @Test
    public void test_validRequest() {

        try {
            response = service.getNumFollow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getNumFollowers());
        Assertions.assertNotNull(response.getNumFollowing());

    }

}
