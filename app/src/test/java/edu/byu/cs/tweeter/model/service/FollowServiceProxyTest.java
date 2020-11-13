package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowServiceProxyTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowServiceProxy addFollowerSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser.getAlias(), new AuthToken(), invalidUser.getAlias(), true);
        invalidRequest = new FollowRequest(invalidUser.getAlias(), new AuthToken(), currentUser.getAlias(), false);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse(true, "Success!", true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new FollowResponse(false, "Failed!", false);
        Mockito.when(mockServerFacade.follow(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a AddFolloweringService instance and wrap it with a spy that will use the mock service
        addFollowerSpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(addFollowerSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetAddFollower_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.getServerFacade().follow(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetAddFollower_validRequest_checksMessage() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.getServerFacade().follow(validRequest, dummyURL);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetAddFollower_invalidRequest_returnsNoAddFollowerees() throws IOException, TweeterRemoteException {
        FollowResponse response = addFollowerSpy.getServerFacade().follow(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
