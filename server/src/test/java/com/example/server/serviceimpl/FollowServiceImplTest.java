package com.example.server.serviceimpl;

import com.example.server.dao.FollowDAO;
import com.example.server.dao.UserDAO;
import com.example.server.service.FollowServiceImpl;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.FollowService;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.FollowResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class FollowServiceImplTest {

    //declare variables
    private FollowRequest validRequest;
    private FollowRequest unfollowRequest;
    private FollowService service;

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

        String userToFollow = "@person1";

        validRequest = new FollowRequest(loggedInUser.getAlias(), authToken, userToFollow, true);
        unfollowRequest = new FollowRequest(loggedInUser.getAlias(), authToken, userToFollow, false);

        service = new FollowServiceImpl();
    }

    @Test
    public void testAddFollower_validRequest_correctResponse() {

        FollowResponse response = new FollowResponse();

        try {
            response = service.follow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    public void testRemoveFollower() {
        FollowResponse response = new FollowResponse();

        try {
            response = service.follow(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getMessage());
    }

}
