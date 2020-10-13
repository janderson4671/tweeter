package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenterTest {

    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusService mockPostStatusService;
    private PostStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Hello", new Date(System.currentTimeMillis()), null);

        request = new PostStatusRequest(status, currentUser);
        response = new PostStatusResponse(true, "Success!");

        mockPostStatusService = Mockito.mock(PostStatusService.class);
        Mockito.when(mockPostStatusService.addPost(request)).thenReturn(response);

        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);
    }

    @Test
    public void testPostStatusReturnsResult() throws IOException {
        Mockito.when(mockPostStatusService.addPost(request)).thenReturn(response);

        Assertions.assertEquals(response, presenter.postStatus(request));
    }

    @Test
    public void testPostStatusThrowsException() throws IOException {
        Mockito.when(mockPostStatusService.addPost(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.postStatus(request);
        });
    }
}
