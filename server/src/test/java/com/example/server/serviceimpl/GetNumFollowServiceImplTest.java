package com.example.server.serviceimpl;

import com.example.server.dao.GetNumFollowDAO;
import com.example.server.service.GetNumFollowServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

public class GetNumFollowServiceImplTest {

    private GetNumFollowRequest validRequest;
    private GetNumFollowRequest invalidRequest;

    private GetNumFollowResponse successResponse;
    private GetNumFollowResponse failureResponse;

    private GetNumFollowServiceImpl postStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new GetNumFollowRequest(currentUser.getAlias(), new AuthToken());
        invalidRequest = new GetNumFollowRequest(currentUser.getAlias(), new AuthToken());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetNumFollowResponse(true, "Success!");
        GetNumFollowDAO mockDAO = Mockito.mock(GetNumFollowDAO.class);
        Mockito.when(mockDAO.getNumFollow(validRequest)).thenReturn(successResponse);

        failureResponse = new GetNumFollowResponse(false, "An exception occured");
        Mockito.when(mockDAO.getNumFollow(invalidRequest)).thenReturn(failureResponse);

        // Create a GetNumFollowingService instance and wrap it with a spy that will use the mock service
        postStatusServiceSpy = Mockito.spy(new GetNumFollowServiceImpl());
        Mockito.when(postStatusServiceSpy.getNumFollowDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testGetNumFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getNumFollowDAO().getNumFollow(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetNumFollow_validRequest_correctMessage() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getNumFollowDAO().getNumFollow(validRequest);

        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testGetNumFollow_invalidRequest_returnsFail() throws IOException, TweeterRemoteException {
        GetNumFollowResponse response = postStatusServiceSpy.getNumFollowDAO().getNumFollow(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
