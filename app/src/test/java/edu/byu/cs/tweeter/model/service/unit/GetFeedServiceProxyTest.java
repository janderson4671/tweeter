package edu.byu.cs.tweeter.model.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.GetFeedServiceProxy;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

public class GetFeedServiceProxyTest {

    private GetFeedRequest validRequest;
    private GetFeedRequest invalidRequest;

    private GetFeedResponse successResponse;
    private GetFeedResponse failureResponse;

    private GetFeedServiceProxy mGetStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new GetFeedRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);
        invalidRequest = new GetFeedRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetFeedResponse(new ArrayList<>(), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStatuses(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new GetFeedResponse("Failed!");
        Mockito.when(mockServerFacade.getStatuses(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a StatusingService instance and wrap it with a spy that will use the mock service
        mGetStatusServiceSpy = Mockito.spy(new GetFeedServiceProxy());
        Mockito.when(mGetStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetFeedResponse response = mGetStatusServiceSpy.getServerFacade().getStatuses(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testStatus_validRequest_returnsStatuses() throws IOException, TweeterRemoteException {
        GetFeedResponse response = mGetStatusServiceSpy.getServerFacade().getStatuses(validRequest, dummyURL);

        Assertions.assertNotNull(response.getStatuses());
    }

    @Test
    public void testStatus_invalidRequest_returnsNoStatus() throws IOException, TweeterRemoteException {
        GetFeedResponse response = mGetStatusServiceSpy.getServerFacade().getStatuses(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
