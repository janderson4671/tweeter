package edu.byu.cs.tweeter.model.service.unit;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.DoesFollowResponse;
import com.example.shared.service.response.FollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.DoesFollowServiceProxy;
import edu.byu.cs.tweeter.model.service.FollowServiceProxy;

public class DoesFollowServiceProxyTest {

    private DoesFollowRequest validRequest;
    private DoesFollowRequest invalidRequest;

    private DoesFollowResponse successResponse;
    private DoesFollowResponse failureResponse;

    private DoesFollowServiceProxy addDoesFollowerSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);

        // Setup request objects to use in the tests
        validRequest = new DoesFollowRequest(new AuthToken(), invalidUser.getAlias(), currentUser.getAlias());
        invalidRequest = new DoesFollowRequest(new AuthToken(), currentUser.getAlias(), invalidUser.getAlias());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new DoesFollowResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.doesFollow(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new DoesFollowResponse(false, "Failed!");
        Mockito.when(mockServerFacade.doesFollow(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a AddDoesFolloweringService instance and wrap it with a spy that will use the mock service
        addDoesFollowerSpy = Mockito.spy(new DoesFollowServiceProxy());
        Mockito.when(addDoesFollowerSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetAddDoesFollower_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        DoesFollowResponse response = addDoesFollowerSpy.getServerFacade().doesFollow(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetAddDoesFollower_validRequest_checksMessage() throws IOException, TweeterRemoteException {
        DoesFollowResponse response = addDoesFollowerSpy.getServerFacade().doesFollow(validRequest, dummyURL);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testInvalid() throws IOException, TweeterRemoteException {
        DoesFollowResponse response = addDoesFollowerSpy.getServerFacade().doesFollow(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
