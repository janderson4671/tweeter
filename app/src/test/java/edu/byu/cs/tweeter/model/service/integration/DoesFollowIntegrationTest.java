package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.DoesFollowResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.DoesFollowServiceProxy;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;

public class DoesFollowIntegrationTest {

    DoesFollowRequest validRequest;
    DoesFollowResponse response;
    static User loggedInUser;
    static AuthToken authToken;

    DoesFollowServiceProxy service;

    @BeforeAll
    static void logInUser() {
        LoginRequest request = new LoginRequest("@person198", "password");
        LoginServiceProxy service = new LoginServiceProxy();
        LoginResponse response;

        try {
            response = service.login(request);
            loggedInUser = response.getUser();
            authToken = response.getAuthToken();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @AfterAll
    static void cleanUp() {
        LogoutRequest request = new LogoutRequest(loggedInUser.getAlias(), authToken);
        LogoutServiceProxy service = new LogoutServiceProxy();

        try {
            service.logout(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @BeforeEach
    public void setup() {

        validRequest = new DoesFollowRequest(authToken, loggedInUser.getAlias(), "@merp");

        service = new DoesFollowServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.doesFollow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isDoesFollow());

    }

}
