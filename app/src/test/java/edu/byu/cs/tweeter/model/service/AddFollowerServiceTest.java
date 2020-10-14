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
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowerResponse;

public class AddFollowerServiceTest {

    private AddFollowerRequest validRequest;
    private AddFollowerRequest invalidRequest;

    private AddFollowerResponse successResponse;
    private AddFollowerResponse failureResponse;

    private AddFollowerService addFollowerSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        User invalidUser = new User("Nathan", "Craddock", null);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()), null);

        // Setup request objects to use in the tests
        validRequest = new AddFollowerRequest(currentUser, new AuthToken(), invalidUser, true);
        invalidRequest = new AddFollowerRequest(invalidUser, new AuthToken(), currentUser, false);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new AddFollowerResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.addFollower(validRequest)).thenReturn(successResponse);

        failureResponse = new AddFollowerResponse(false, "Failed!");
        Mockito.when(mockServerFacade.addFollower(invalidRequest)).thenReturn(failureResponse);

        // Create a AddFolloweringService instance and wrap it with a spy that will use the mock service
        addFollowerSpy = Mockito.spy(new AddFollowerService());
        Mockito.when(addFollowerSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetAddFollower_validRequest_correctResponse() throws IOException {
        AddFollowerResponse response = addFollowerSpy.addFollower(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetAddFollower_validRequest_loadsProfileImages() throws IOException {
        AddFollowerResponse response = addFollowerSpy.addFollower(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetAddFollower_invalidRequest_returnsNoAddFollowerees() throws IOException {
        AddFollowerResponse response = addFollowerSpy.addFollower(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
