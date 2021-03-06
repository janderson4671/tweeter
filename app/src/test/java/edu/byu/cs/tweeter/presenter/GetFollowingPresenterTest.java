package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowingService;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

public class GetFollowingPresenterTest {

    private GetFollowingRequest request;
    private GetFollowingResponse response;
    private GetFollowingService mMockGetFollowingService;
    private GetFollowingPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", 0, 0);
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", 0, 0);

        request = new GetFollowingRequest(currentUser.getAlias(), new AuthToken(), 10, null);
        response = new GetFollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        // Create a mock FollowingService
        mMockGetFollowingService = Mockito.mock(GetFollowingService.class);
        Mockito.when(mMockGetFollowingService.getFollowing(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new GetFollowingPresenter(new GetFollowingPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mMockGetFollowingService);
    }

    @Test
    public void testGetFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetFollowingService.getFollowing(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.getFollowing(request));
    }

    @Test
    public void testGetFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetFollowingService.getFollowing(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowing(request);
        });
    }
}
