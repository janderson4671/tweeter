package edu.byu.cs.tweeter.model.service.unit;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.GetNumFollowServiceProxy;

public class GetNumFollowProxyTest {

    private GetNumFollowRequest validRequest;
    private GetNumFollowRequest invalidRequest;

    private GetNumFollowResponse successResponse;
    private GetNumFollowResponse failureResponse;

    private GetNumFollowServiceProxy postStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new GetNumFollowRequest(currentUser.getAlias(), new AuthToken());
        invalidRequest = new GetNumFollowRequest(currentUser.getAlias(), new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetNumFollowResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getNumFollow(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new GetNumFollowResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.getNumFollow(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a GetNumFollowingService instance and wrap it with a spy that will use the mock service
        postStatusServiceSpy = Mockito.spy(new GetNumFollowServiceProxy());
        Mockito.when(postStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetNumFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getServerFacade().getNumFollow(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetNumFollow_validRequest_correctMessage() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getServerFacade().getNumFollow(validRequest, dummyURL);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetNumFollow_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getServerFacade().getNumFollow(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
