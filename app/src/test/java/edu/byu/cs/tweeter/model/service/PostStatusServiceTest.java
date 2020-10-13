package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusServiceTest {

    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusService followingServiceSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()), null);

        // Setup request objects to use in the tests
        validRequest = new PostStatusRequest(stuatus, currentUser);
        invalidRequest = new PostStatusRequest(stuatus, currentUser);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostStatusResponse(true, "Success!");
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest)).thenReturn(successResponse);

        failureResponse = new PostStatusResponse(false, "An exception occured");
        Mockito.when(mockServerFacade.postStatus(invalidRequest)).thenReturn(failureResponse);

        // Create a PostStatusingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetPostStatusees_validRequest_correctResponse() throws IOException {
        PostStatusResponse response = followingServiceSpy.addPost(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetPostStatusees_validRequest_loadsProfileImages() throws IOException {
        PostStatusResponse response = followingServiceSpy.addPost(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetPostStatusees_invalidRequest_returnsNoPostStatusees() throws IOException {
        PostStatusResponse response = followingServiceSpy.addPost(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
