package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowServiceProxy addFollowerSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User invalidUser = new User("Nathan", "Craddock", null);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()), null);

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser, new AuthToken(), invalidUser, true);
        invalidRequest = new FollowRequest(invalidUser, new AuthToken(), currentUser, false);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new FollowResponse(false, "Failed!");
        Mockito.when(mockServerFacade.follow(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a AddFolloweringService instance and wrap it with a spy that will use the mock service
        addFollowerSpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(addFollowerSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetAddFollower_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.follow(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetAddFollower_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.follow(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetAddFollower_invalidRequest_returnsNoAddFollowerees() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.follow(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
