package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.GetStatusService;
import edu.byu.cs.tweeter.model.service.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetStatusResponse;

public class GetStatusPresenterTest {

    private GetStatusRequest request;
    private GetStatusResponse response;
    private GetStatusService mMockGetStatusService;
    private GetStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new GetStatusRequest(currentUser, new AuthToken(), 10, null, 2);
        response = new GetStatusResponse(new ArrayList<Status>(), false);

        mMockGetStatusService = Mockito.mock(GetStatusService.class);
        Mockito.when(mMockGetStatusService.getStatuses(request)).thenReturn(response);

        presenter = Mockito.spy(new GetStatusPresenter(new GetStatusPresenter.View() {}));
        Mockito.when(presenter.getStatusService()).thenReturn(mMockGetStatusService);
    }

    @Test
    public void testStatusReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetStatusService.getStatuses(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.getStatuses(request));
    }

    @Test
    public void testStatusThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mMockGetStatusService.getStatuses(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStatuses(request);
        });
    }

}
