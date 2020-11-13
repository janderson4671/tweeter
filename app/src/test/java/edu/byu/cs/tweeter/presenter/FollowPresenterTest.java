package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.FollowService;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowPresenterTest {

    private FollowRequest request;
    private FollowResponse response;
    private FollowService mMockFollowService;
    private FollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User followUser = new User("Jason", "Anderson", null, 0, 0);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()).toString(), null);

        request = new FollowRequest(currentUser.getAlias(), new AuthToken(), followUser.getAlias(), true);
        response = new FollowResponse(true, "Success!", true);

        mMockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mMockFollowService.follow(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowPresenter(new FollowPresenter.View() {}));
        Mockito.when(presenter.getAddFollowerService()).thenReturn(mMockFollowService);
    }

    @Test
    public void testAddFollowerReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowService.follow(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.follow(request));
    }

    @Test
    public void testAddFollowerThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mMockFollowService.follow(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.follow(request);
        });
    }

}
