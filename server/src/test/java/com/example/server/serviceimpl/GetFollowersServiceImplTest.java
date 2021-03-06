package com.example.server.serviceimpl;

import com.example.server.service.GetFollowersServiceImpl;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.GetFollowersResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetFollowersServiceImplTest {

    private GetFollowersRequest validRequest;
    private GetFollowersService service;

    static User loggedInUser;
    static AuthToken authToken;

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

        validRequest = new GetFollowersRequest(loggedInUser.getAlias(), authToken, 10, null);

        service = new GetFollowersServiceImpl();
    }

    @Test
    public void testGetFollowers() {

        GetFollowersResponse response;

        try {
            response = service.getFollowers(validRequest);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getHasMorePages());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
