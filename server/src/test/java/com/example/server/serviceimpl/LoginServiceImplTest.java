package com.example.server.serviceimpl;

import com.example.server.dao.LoginDAO;
import com.example.server.service.LoginServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LoginServiceImplTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginServiceImpl loginServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        // Setup request objects to use in the tests
        validRequest = new LoginRequest("Test", "12345678");
        invalidRequest = new LoginRequest("Fail", "asdfjkl");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(currentUser, new AuthToken());
        LoginDAO mockDAO = Mockito.mock(LoginDAO.class);
        Mockito.when(mockDAO.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginResponse("An exception occured");
        Mockito.when(mockDAO.login(invalidRequest)).thenReturn(failureResponse);

        // Create a LoginingService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LoginServiceImpl());
        Mockito.when(loginServiceSpy.getLoginDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.getLoginDAO().login(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogin_validRequest_hasAlias() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.getLoginDAO().login(validRequest);

        Assertions.assertNotNull(response.getUser().getAlias());
        Assertions.assertEquals("@FirstNameLastName", response.getUser().getAlias());
    }

    @Test
    public void GetLogin_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceSpy.getLoginDAO().login(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
