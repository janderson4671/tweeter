package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.GetFeedService;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

public class GetFeedPresenterTest {

    private GetFeedRequest request;
    private GetFeedResponse response;
    private GetFeedService mMockGetFeedService;
    private GetFeedPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new GetFeedRequest(currentUser, new AuthToken(), 10, null, 2);
        response = new GetFeedResponse(new ArrayList<Status>(), false);

        mMockGetFeedService = Mockito.mock(GetFeedService.class);
        Mockito.when(mMockGetFeedService.getStatuses(request)).thenReturn(response);

        presenter = Mockito.spy(new GetFeedPresenter(new GetFeedPresenter.View() {}));
        Mockito.when(presenter.getStatusService()).thenReturn(mMockGetFeedService);
    }

    @Test
    public void testStatusReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetFeedService.getStatuses(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.getStatuses(request));
    }

    @Test
    public void testStatusThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetFeedService.getStatuses(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStatuses(request);
        });
    }

}