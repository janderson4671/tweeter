package edu.byu.cs.tweeter.model.service.unit;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.GetStoryServiceProxy;

public class GetStoryServiceProxyTest {

    private GetStoryRequest validRequest;
    private GetStoryRequest invalidRequest;

    private GetStoryResponse successResponse;
    private GetStoryResponse failureResponse;

    private GetStoryServiceProxy mGetStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new GetStoryRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);
        invalidRequest = new GetStoryRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetStoryResponse(new ArrayList<>(), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new GetStoryResponse("Failed!");
        Mockito.when(mockServerFacade.getStory(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a StatusingService instance and wrap it with a spy that will use the mock service
        mGetStatusServiceSpy = Mockito.spy(new GetStoryServiceProxy());
        Mockito.when(mGetStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getServerFacade().getStory(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testStatus_validRequest_returnsStatuses() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getServerFacade().getStory(validRequest, dummyURL);

        Assertions.assertNotNull(response.getStatuses());
    }

    @Test
    public void testStatus_invalidRequest_returnsNoStatus() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getServerFacade().getStory(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
