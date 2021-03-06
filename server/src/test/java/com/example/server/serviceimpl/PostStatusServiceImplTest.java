package com.example.server.serviceimpl;

import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.server.service.PostStatusServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.PostStatusService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.LoginResponse;
import com.example.shared.service.response.PostStatusResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PostStatusServiceImplTest {

    private PostStatusRequest validRequest;
    private PostStatusService service;

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

        Status status = new Status(loggedInUser, "Hello There!", LocalDateTime.now().toString(), null);

        validRequest = new PostStatusRequest(status, loggedInUser.getAlias(), authToken);

        service = new PostStatusServiceImpl();
    }

    @Test
    public void testPostStatus() {

        PostStatusResponse response;

        try {
            response = service.postStatus(validRequest);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getMessage());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
