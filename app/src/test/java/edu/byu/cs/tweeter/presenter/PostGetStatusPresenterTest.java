package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.PostStatusService;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostGetStatusPresenterTest {

    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusService mockPostStatusService;
    private PostStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new PostStatusRequest(status, currentUser);
        response = new PostStatusResponse(true, "Success!");

        mockPostStatusService = Mockito.mock(PostStatusService.class);
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);
    }

    @Test
    public void testPostStatusReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.postStatus(request));
    }

    @Test
    public void testPostStatusThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostStatusService.postStatus(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.postStatus(request);
        });
    }
}
