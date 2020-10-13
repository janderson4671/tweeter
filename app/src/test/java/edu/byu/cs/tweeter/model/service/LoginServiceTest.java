package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginService followingServiceSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        // Setup request objects to use in the tests
        validRequest = new LoginRequest("Test", "12345678");
        invalidRequest = new LoginRequest("Fail", "asdfjkl");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(currentUser, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginResponse("An exception occured");
        Mockito.when(mockServerFacade.login(invalidRequest)).thenReturn(failureResponse);

        // Create a LoginingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetLoginees_validRequest_correctResponse() throws IOException {
        LoginResponse response = followingServiceSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetLoginees_validRequest_loadsProfileImages() throws IOException {
        LoginResponse response = followingServiceSpy.login(validRequest);

        Assertions.assertNotNull(response.getUser().getAlias());
    }

    @Test
    public void testGetLoginees_invalidRequest_returnsNoLoginees() throws IOException {
        LoginResponse response = followingServiceSpy.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}