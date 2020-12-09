package com.example.server.serviceimpl;

import com.example.server.dao.GetNumFollowDAO;
import com.example.server.service.GetFollowersServiceImpl;
import com.example.server.service.GetNumFollowServiceImpl;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowersService;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.GetFollowersResponse;
import com.example.shared.service.response.GetNumFollowResponse;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

public class GetNumFollowServiceImplTest {

    private GetNumFollowRequest validRequest;
    private GetNumFollowService service;

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

        validRequest = new GetNumFollowRequest(loggedInUser.getAlias(), authToken);

        service = new GetNumFollowServiceImpl();
    }

    @Test
    public void testGetNumFollow() {

        GetNumFollowResponse response;

        try {
            response = service.getNumFollow(validRequest);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getNumFollowers());
            Assertions.assertNotNull(response.getNumFollowing());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
