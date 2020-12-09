package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.GetStoryResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.model.service.GetStoryServiceProxy;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;

public class GetStoryIntegrationTest {

    GetStoryRequest validRequest;
    GetStoryResponse response;
    static User loggedInUser;
    static AuthToken authToken;

    GetStoryServiceProxy service;

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

        validRequest = new GetStoryRequest(loggedInUser.getAlias(), authToken, 12, null);
        service = new GetStoryServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.getStatuses(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getHasMorePages());


    }

}
