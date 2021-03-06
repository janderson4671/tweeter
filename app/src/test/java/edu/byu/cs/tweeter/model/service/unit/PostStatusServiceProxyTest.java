package edu.byu.cs.tweeter.model.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.PostStatusServiceProxy;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusServiceProxyTest {

    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceProxy postStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new PostStatusRequest(stuatus, currentUser.getAlias(), new AuthToken());
        invalidRequest = new PostStatusRequest(stuatus, currentUser.getAlias(), new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostStatusResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new PostStatusResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.postStatus(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a PostStatusingService instance and wrap it with a spy that will use the mock service
        postStatusServiceSpy = Mockito.spy(new PostStatusServiceProxy());
        Mockito.when(postStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getServerFacade().postStatus(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testPostStatus_validRequest_correctMessage() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getServerFacade().postStatus(validRequest, dummyURL);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testPostStatus_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getServerFacade().postStatus(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
