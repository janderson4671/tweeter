package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusPresenterTest {

    private StatusRequest request;
    private StatusResponse response;
    private StatusService mockStatusService;
    private StatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new StatusRequest(currentUser, new AuthToken(), 10, null, 2);
        response = new StatusResponse(new ArrayList<Status>(), false);

        mockStatusService = Mockito.mock(StatusService.class);
        Mockito.when(mockStatusService.getStatuses(request)).thenReturn(response);

        presenter = Mockito.spy(new StatusPresenter(new StatusPresenter.View() {}));
        Mockito.when(presenter.getStatusService()).thenReturn(mockStatusService);
    }

    @Test
    public void testStatusReturnsResult() throws IOException {
        Mockito.when(mockStatusService.getStatuses(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.getStatuses(request));
    }

    @Test
    public void testStatusThrowsException() throws IOException {
        Mockito.when(mockStatusService.getStatuses(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStatuses(request);
        });
    }

}
