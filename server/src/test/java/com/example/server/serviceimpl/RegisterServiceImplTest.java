package com.example.server.serviceimpl;

import com.example.server.dao.RegisterDAO;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.server.service.RegisterServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.RegisterService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.LoginResponse;
import com.example.shared.service.response.RegisterResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class RegisterServiceImplTest {

    static User loggedInUser;
    static AuthToken authToken;

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
        byte [] profile;

        try {
            profile = ByteArrayUtils.bytesFromUrl("https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40blah");
            validRequest = new RegisterRequest(firstName, lastName, username, password, profile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void registerInUser() {
        RegisterRequest request = new RegisterRequest("Person 198", "Test", "@person198", "password");
        RegisterService service = new RegisterServiceImpl();
        RegisterResponse response;

        try {
            response = service.login(request);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getUser());
            Assertions.assertNotNull(response.getAuthToken());

            loggedInUser = response.getUser();
            authToken = response.getAuthToken();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

}
