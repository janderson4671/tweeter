package com.example.server.serviceimpl;

import com.example.server.dao.PostStatusDAO;
import com.example.server.service.PostStatusServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

public class PostStatusServiceImplTest {

    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceImpl postStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new PostStatusRequest(stuatus, currentUser.getAlias(), new AuthToken());
        invalidRequest = new PostStatusRequest(stuatus, currentUser.getAlias(), new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostStatusResponse(true, "Success!");
        PostStatusDAO mockDAO = Mockito.mock(PostStatusDAO.class);
        Mockito.when(mockDAO.postStatus(validRequest)).thenReturn(successResponse);

        failureResponse = new PostStatusResponse(false, "An exception occured");
        Mockito.when(mockDAO.postStatus(invalidRequest)).thenReturn(failureResponse);

        // Create a PostStatusingService instance and wrap it with a spy that will use the mock service
        postStatusServiceSpy = Mockito.spy(new PostStatusServiceImpl());
        Mockito.when(postStatusServiceSpy.getPostStatusDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getPostStatusDAO().postStatus(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testPostStatus_validRequest_correctMessage() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getPostStatusDAO().postStatus(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testPostStatus_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceSpy.getPostStatusDAO().postStatus(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
