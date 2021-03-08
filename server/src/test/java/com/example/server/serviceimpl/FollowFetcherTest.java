package com.example.server.serviceimpl;

import com.example.server.service.FollowFetcherService;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.JsonSerializer;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FollowFetcherTest {

    private FollowFetcherService service;

    static User loggedInUser;
    static AuthToken authToken;

    Status status;

    @BeforeAll
    static void logInUser() {
        LoginRequest request = new LoginRequest("@person198", "password");
        LoginService service = new LoginServiceImpl();
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
        LogoutService service = new LogoutServiceImpl();

        try {
            service.logout(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @BeforeEach
    public void setup() {

        service = new FollowFetcherService();
        status = new Status(loggedInUser, "Test", "today at some time", null);

    }

    @Test
    public void testFollowFetch() {

        try {
            String statusStr = JsonSerializer.serialize(status);

            String message = service.fetchAndPost(statusStr);

            Assertions.assertNotNull(message);
            Assertions.assertEquals(message, "Success");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
