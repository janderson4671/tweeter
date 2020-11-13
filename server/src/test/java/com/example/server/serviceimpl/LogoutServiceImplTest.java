package com.example.server.serviceimpl;

import com.example.server.dao.LogoutDAO;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LogoutServiceImplTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutServiceImpl logoutServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(currentUser.getAlias(), new AuthToken());
        invalidRequest = new LogoutRequest(currentUser.getAlias(), new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse(true, "Success!");
        LogoutDAO mockServerFacade = Mockito.mock(LogoutDAO.class);
        Mockito.when(mockServerFacade.logout(validRequest)).thenReturn(successResponse);

        failureResponse = new LogoutResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.logout(invalidRequest)).thenReturn(failureResponse);

        // Create a LogoutingService instance and wrap it with a spy that will use the mock service
        logoutServiceSpy = Mockito.spy(new LogoutServiceImpl());
        Mockito.when(logoutServiceSpy.getLogoutDAO()).thenReturn(mockServerFacade);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceSpy.getLogoutDAO().logout(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogout_validRequest_correctMessage() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceSpy.getLogoutDAO().logout(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testLogout_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceSpy.getLogoutDAO().logout(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
