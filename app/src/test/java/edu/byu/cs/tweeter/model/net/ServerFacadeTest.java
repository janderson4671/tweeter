package edu.byu.cs.tweeter.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.GetFollowingRequest;
import edu.byu.cs.tweeter.model.service.response.GetFollowingResponse;

class ServerFacadeTest {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees
    private final User user6 = new User("Mother", "Teresa", ""); // 7 followees
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");

    private ServerFacade serverFacadeSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());
    }

    @Test
    void testGetFollowees_noFolloweesForUser() throws IOException, TweeterRemoteException {

        List<User> followees = Arrays.asList();
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        GetFollowingRequest request = new GetFollowingRequest(user1, new AuthToken(), 0, null, 2);
        GetFollowingResponse response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() throws IOException, TweeterRemoteException {

        List<User> followees = Arrays.asList(user2);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        GetFollowingRequest request = new GetFollowingRequest(user2, new AuthToken(), 1, null, 2);
        GetFollowingResponse response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() throws IOException, TweeterRemoteException {

        List<User> followees = Arrays.asList(user2, user3);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        GetFollowingRequest request = new GetFollowingRequest(user3, new AuthToken(), 2, null, 2);
        GetFollowingResponse response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() throws IOException, TweeterRemoteException {

        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        GetFollowingRequest request = new GetFollowingRequest(user5,new AuthToken(),2, null, 2);
        GetFollowingResponse response = serverFacadeSpy.getFollowing(request, dummyURL);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new GetFollowingRequest(user5, new AuthToken(), 2, response.getFollowees().get(1), 2);
        response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new GetFollowingRequest(user5, new AuthToken(), 2, response.getFollowees().get(1), 2);
        response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user6));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() throws IOException, TweeterRemoteException {

        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
        Mockito.when(serverFacadeSpy.getDummyFollowees()).thenReturn(followees);

        GetFollowingRequest request = new GetFollowingRequest(user6, new AuthToken(), 2, null, 2);
        GetFollowingResponse response = serverFacadeSpy.getFollowing(request, dummyURL);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new GetFollowingRequest(user6, new AuthToken(),2, response.getFollowees().get(1), 2);
        response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new GetFollowingRequest(user6, new AuthToken(), 2, response.getFollowees().get(1), 2);
        response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user6));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new GetFollowingRequest(user6, new AuthToken(), 2, response.getFollowees().get(1), 2);
        response = serverFacadeSpy.getFollowing(request, dummyURL);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }
}
