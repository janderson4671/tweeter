package com.example.server.serviceimpl;

import com.example.server.dao.GetStoryDAO;
import com.example.server.service.GetStoryServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GetStoryServiceImplTest {

    private GetStoryRequest validRequest;
    private GetStoryRequest invalidRequest;

    private GetStoryResponse successResponse;
    private GetStoryResponse failureResponse;

    private GetStoryServiceImpl mGetStatusServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);
        Status stuatus = new Status(currentUser, "Test", new Date(System.currentTimeMillis()).toString(), null);

        // Setup request objects to use in the tests
        validRequest = new GetStoryRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);
        invalidRequest = new GetStoryRequest(currentUser.getAlias(), new AuthToken(), 10, stuatus);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new GetStoryResponse(new ArrayList<>(), false);
        GetStoryDAO mockDAO = Mockito.mock(GetStoryDAO.class);
        Mockito.when(mockDAO.getStatuses(validRequest)).thenReturn(successResponse);

        failureResponse = new GetStoryResponse("Failed!");
        Mockito.when(mockDAO.getStatuses(invalidRequest)).thenReturn(failureResponse);

        // Create a StatusingService instance and wrap it with a spy that will use the mock service
        mGetStatusServiceSpy = Mockito.spy(new GetStoryServiceImpl());
        Mockito.when(mGetStatusServiceSpy.getStoryDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getStoryDAO().getStatuses(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testStatus_validRequest_returnsStatuses() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getStoryDAO().getStatuses(validRequest);

        Assertions.assertNotNull(response.getStatuses());
    }

    @Test
    public void testStatus_invalidRequest_returnsNoStatus() throws IOException, TweeterRemoteException {
        GetStoryResponse response = mGetStatusServiceSpy.getStoryDAO().getStatuses(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
