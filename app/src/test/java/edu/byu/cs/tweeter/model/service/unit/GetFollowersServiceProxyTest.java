package edu.byu.cs.tweeter.model.service.unit;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.GetFollowersServiceProxy;

public class GetFollowersServiceProxyTest {

    private GetFollowersRequest validRequest;
    private GetFollowersRequest invalidRequest;

    private GetFollowersResponse successResponse;
    private GetFollowersResponse failureResponse;

    private GetFollowersServiceProxy mGetFollowersServiceSpy;

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
        validRequest = new GetFollowersRequest(currentUser.getAlias(), new AuthToken(), 3, null);
        invalidRequest = new GetFollowersRequest(null, new AuthToken(), 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetFollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new GetFollowersResponse("An exception occured");
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        mGetFollowersServiceSpy = Mockito.spy(new GetFollowersServiceProxy());
        Mockito.when(mGetFollowersServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetFollowersResponse response = mGetFollowersServiceSpy.getServerFacade().getFollowers(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        GetFollowersResponse response = mGetFollowersServiceSpy.getServerFacade().getFollowers(validRequest, dummyURL);

        for(User user : response.getUsers()) {
            Assertions.assertNotNull(user.getFirstName());
            Assertions.assertNotNull(user.getAlias());
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        GetFollowersResponse response = mGetFollowersServiceSpy.getServerFacade().getFollowers(invalidRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
