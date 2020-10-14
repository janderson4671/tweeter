package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusServiceTest {

    private StatusRequest validRequest;
    private StatusRequest invalidRequest;

    private StatusResponse successResponse;
    private StatusResponse failureResponse;

    private StatusService statusServiceSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        User invalidUser = new User("Nathan", "Craddock", null);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()), null);

        // Setup request objects to use in the tests
        validRequest = new StatusRequest(currentUser, new AuthToken(), 10, stuatus, 3);
        invalidRequest = new StatusRequest(currentUser, new AuthToken(), 10, stuatus, 3);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusResponse(new ArrayList<>(), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusResponse("Failed!");
        Mockito.when(mockServerFacade.getFeed(invalidRequest)).thenReturn(failureResponse);

        // Create a StatusingService instance and wrap it with a spy that will use the mock service
        statusServiceSpy = Mockito.spy(new StatusService());
        Mockito.when(statusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testStatus_validRequest_correctResponse() throws IOException {
        StatusResponse response = statusServiceSpy.getStatuses(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testStatus_validRequest_returnsStatuses() throws IOException {
        StatusResponse response = statusServiceSpy.getStatuses(validRequest);

        Assertions.assertNotNull(response.getStatuses());
    }

    @Test
    public void testStatus_invalidRequest_returnsNoStatus() throws IOException {
        StatusResponse response = statusServiceSpy.getStatuses(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
