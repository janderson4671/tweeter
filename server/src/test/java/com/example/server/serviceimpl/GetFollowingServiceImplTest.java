package com.example.server.serviceimpl;

import com.example.server.dao.GetFollowingDAO;
import com.example.server.service.GetFollowersServiceImpl;
import com.example.server.service.GetFollowingServiceImpl;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.GetFollowingService;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.GetFollowersResponse;
import com.example.shared.service.response.GetFollowingResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class GetFollowingServiceImplTest {

    private GetFollowingRequest validRequest;
    private GetFollowingService service;

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

        validRequest = new GetFollowingRequest(loggedInUser.getAlias(), authToken, 10, null);

        service = new GetFollowingServiceImpl();
    }

    @Test
    public void testAddFollower_validRequest_correctResponse() {

        GetFollowingResponse response;

        try {
            response = service.getFollowing(validRequest);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getHasMorePages());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
