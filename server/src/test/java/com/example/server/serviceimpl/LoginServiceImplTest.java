package com.example.server.serviceimpl;

import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginServiceImplTest {

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

    @Test
    public void logInUser() {
        LoginRequest request = new LoginRequest("@person198", "password");
        LoginService service = new LoginServiceImpl();
        LoginResponse response;

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
