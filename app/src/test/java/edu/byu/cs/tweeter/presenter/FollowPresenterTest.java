package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowPresenterTest {

    private FollowRequest request;
    private FollowResponse response;
    private FollowService mMockFollowService;
    private FollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        User followUser = new User("Jason", "Anderson", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new FollowRequest(currentUser, new AuthToken(), followUser, true);
        response = new FollowResponse(true, "Success!");

        mMockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mMockFollowService.addFollower(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowPresenter(new FollowPresenter.View() {}));
        Mockito.when(presenter.getAddFollowerService()).thenReturn(mMockFollowService);
    }

    @Test
    public void testAddFollowerReturnsResult() throws IOException {
        Mockito.when(mMockFollowService.addFollower(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.addFollower(request));
    }

    @Test
    public void testAddFollowerThrowsException() throws IOException {
        Mockito.when(mMockFollowService.addFollower(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.addFollower(request);
        });
    }

}
