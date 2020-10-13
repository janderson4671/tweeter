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
import edu.byu.cs.tweeter.model.service.AddFollowerService;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class AddFollowerPresenterTest {

    private AddFollowerRequest request;
    private Response response;
    private AddFollowerService mockAddFollowerService;
    private AddFollowerPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        User followUser = new User("Jason", "Anderson", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new AddFollowerRequest(currentUser, new AuthToken(), followUser, true);
        response = new Response(true, "Success!");

        mockAddFollowerService = Mockito.mock(AddFollowerService.class);
        Mockito.when(mockAddFollowerService.addFollower(request)).thenReturn(response);

        presenter = Mockito.spy(new AddFollowerPresenter(new AddFollowerPresenter.View() {}));
        Mockito.when(presenter.getAddFollowerService()).thenReturn(mockAddFollowerService);
    }

    @Test
    public void testAddFollowerReturnsResult() throws IOException {
        Mockito.when(mockAddFollowerService.addFollower(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.addFollower(request));
    }

    @Test
    public void testAddFollowerThrowsException() throws IOException {
        Mockito.when(mockAddFollowerService.addFollower(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.addFollower(request);
        });
    }

}
