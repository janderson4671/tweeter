package com.example.server.serviceimpl;

import com.example.server.dao.GetFollowingDAO;
import com.example.server.service.GetFollowingServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class GetFollowingServiceImplTest {

    private GetFollowingRequest validRequest;
    private GetFollowingRequest invalidRequest;

    private GetFollowingResponse successResponse;
    private GetFollowingResponse failureResponse;

    private GetFollowingServiceImpl mGetFollowingServiceSpy;

    private String dummyURL = "/helloworld";

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", 0, 0);
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", 0, 0);

        // Setup request objects to use in the tests
        validRequest = new GetFollowingRequest(currentUser.getAlias(), new AuthToken(), 3, null);
        invalidRequest = new GetFollowingRequest(null, new AuthToken(), 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetFollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        GetFollowingDAO mockDAO = Mockito.mock(GetFollowingDAO.class);
        Mockito.when(mockDAO.getFollowing(validRequest)).thenReturn(successResponse);

        failureResponse = new GetFollowingResponse("An exception occured");
        Mockito.when(mockDAO.getFollowing(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        mGetFollowingServiceSpy = Mockito.spy(new GetFollowingServiceImpl());
        Mockito.when(mGetFollowingServiceSpy.getFollowingDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetFollowingResponse response = mGetFollowingServiceSpy.getFollowingDAO().getFollowing(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        GetFollowingResponse response = mGetFollowingServiceSpy.getFollowingDAO().getFollowing(validRequest);

        for(User user : response.getUsers()) {
            Assertions.assertNotNull(user.getFirstName());
            Assertions.assertNotNull(user.getAlias());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        GetFollowingResponse response = mGetFollowingServiceSpy.getFollowingDAO().getFollowing(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
