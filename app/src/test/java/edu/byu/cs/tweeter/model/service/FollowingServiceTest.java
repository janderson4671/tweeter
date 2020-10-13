package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowingServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowingService followingServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser, new AuthToken(), 3, null,2);
        invalidRequest = new FollowRequest(null, new AuthToken(), 0, null,2);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowees(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse("An exception occured");
        Mockito.when(mockServerFacade.getFollowees(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new FollowingService());
        Mockito.when(followingServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowResponse response = followingServiceSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        FollowResponse response = followingServiceSpy.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        FollowResponse response = followingServiceSpy.getFollowees(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
