package com.example.server.serviceimpl;

import com.example.server.dao.FollowDAO;
import com.example.server.service.FollowServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FollowServiceImplTest {

    //declare variables
    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowServiceImpl addFollowerSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);
        User invalidUser = new User("Nathan", "Craddock", null, 0, 0);

        validRequest = new FollowRequest(currentUser.getAlias(), new AuthToken(), invalidUser.getAlias(), true);
        invalidRequest = new FollowRequest(invalidUser.getAlias(), new AuthToken(), currentUser.getAlias(), false);

        successResponse = new FollowResponse(true, "Success!", true);
        FollowDAO mockDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(mockDAO.follow(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse(false, "Failed!", false);
        Mockito.when(mockDAO.follow(invalidRequest)).thenReturn(failureResponse);

        addFollowerSpy = Mockito.spy(new FollowServiceImpl());
        Mockito.when(addFollowerSpy.getFollowDAO()).thenReturn(mockDAO);
    }

    @Test
    public void testAddFollower_validRequest_correctResponse() {
        FollowResponse response = addFollowerSpy.getFollowDAO().follow(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testAddFollower_validRequest_correctMessage() {
        FollowResponse response = addFollowerSpy.getFollowDAO().follow(validRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testAddFollower_invalidRequest_incorrectResponse() {
        FollowResponse response = addFollowerSpy.getFollowDAO().follow(invalidRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }
}
