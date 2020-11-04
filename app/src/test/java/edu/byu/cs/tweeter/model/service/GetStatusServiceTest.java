package edu.byu.cs.tweeter.model.service;

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
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.request.GetStatusRequest;
import com.example.shared.service.response.GetStatusResponse;

public class GetStatusServiceTest {

    private GetStatusRequest validRequest;
    private GetStatusRequest invalidRequest;

    private GetStatusResponse successResponse;
    private GetStatusResponse failureResponse;

    private GetStatusServiceProxy mGetStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User invalidUser = new User("Nathan", "Craddock", null);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()), null);

        // Setup request objects to use in the tests
        validRequest = new GetStatusRequest(currentUser, new AuthToken(), 10, stuatus, 3);
        invalidRequest = new GetStatusRequest(currentUser, new AuthToken(), 10, stuatus, 3);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetStatusResponse(new ArrayList<>(), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStatuses(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new GetStatusResponse("Failed!");
        Mockito.when(mockServerFacade.getStatuses(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a StatusingService instance and wrap it with a spy that will use the mock service
        mGetStatusServiceSpy = Mockito.spy(new GetStatusServiceProxy());
        Mockito.when(mGetStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetStatusResponse response = mGetStatusServiceSpy.getStatuses(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testStatus_validRequest_returnsStatuses() throws IOException, TweeterRemoteException {
        GetStatusResponse response = mGetStatusServiceSpy.getStatuses(validRequest);

        Assertions.assertNotNull(response.getStatuses());
    }

    @Test
    public void testStatus_invalidRequest_returnsNoStatus() throws IOException, TweeterRemoteException {
        GetStatusResponse response = mGetStatusServiceSpy.getStatuses(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
