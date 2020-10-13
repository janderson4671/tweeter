package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterService followingServiceSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        // Setup request objects to use in the tests
        validRequest = new RegisterRequest("Jason", "Anderson", "@jasonAnderson", "12345678", null);
        invalidRequest = new RegisterRequest("Nathan", "Craddock", "@merp", "12348765", null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(currentUser, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occured");
        Mockito.when(mockServerFacade.register(invalidRequest)).thenReturn(failureResponse);

        // Create a RegisteringService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new RegisterService());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetRegisterees_validRequest_correctResponse() throws IOException {
        RegisterResponse response = followingServiceSpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetRegisterees_validRequest_loadsProfileImages() throws IOException {
        RegisterResponse response = followingServiceSpy.register(validRequest);

        Assertions.assertNotNull(response.getUser().getAlias());
    }

    @Test
    public void testGetRegisterees_invalidRequest_returnsNoRegisterees() throws IOException {
        RegisterResponse response = followingServiceSpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}