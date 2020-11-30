package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.GetNumFollowServiceProxy;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;

public class LogoutIntegrationTest {

    LogoutRequest validRequest;
    LogoutServiceProxy service;

    LogoutResponse response;

    User loggedInUser;

    @BeforeEach
    public void setup() {

        loggedInUser = new User("Fran", "Franklin", "@FranFranklin",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new LogoutRequest(loggedInUser.getAlias(), new AuthToken());

        service = new LogoutServiceProxy();
    }

    @Test
    public void test_validRequest() {

        try {
            response = service.logout(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());

    }

}
