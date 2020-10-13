package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutService followingServiceSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(currentUser, new AuthToken());
        invalidRequest = new LogoutRequest(currentUser, new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest)).thenReturn(successResponse);

        failureResponse = new LogoutResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.logout(invalidRequest)).thenReturn(failureResponse);

        // Create a LogoutingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetLogoutees_validRequest_correctResponse() throws IOException {
        LogoutResponse response = followingServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetLogoutees_validRequest_loadsProfileImages() throws IOException {
        LogoutResponse response = followingServiceSpy.logout(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetLogoutees_invalidRequest_returnsNoLogoutees() throws IOException {
        LogoutResponse response = followingServiceSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}