package com.example.server.dao;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetFollowingDAOTest {

    private GetFollowingRequest validRequest;
    private int limit;
    User currentUser;

    @BeforeEach
    public void setup() {

        currentUser = new User("Jason", "Anderson", null, 2000, 3458);

        limit = 12;
        validRequest = new GetFollowingRequest(currentUser.getAlias(), new AuthToken(), limit, null);
    }

    @Test
    public void testPagination() {
        GetFollowingDAO getFollowingDAO = new GetFollowingDAO();

        GetFollowingResponse response = getFollowingDAO.getFollowing(validRequest);
        String lastFollowing = response.getUsers().get(response.getUsers().size() - 1).getAlias();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getHasMorePages());
        Assertions.assertEquals(lastFollowing,"@FranFranklin");

        validRequest = new GetFollowingRequest(currentUser.getAlias(), new AuthToken(), limit, lastFollowing);

        response = getFollowingDAO.getFollowing(validRequest);
        lastFollowing = response.getUsers().get(response.getUsers().size() - 1).getAlias();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getHasMorePages());
        Assertions.assertEquals(lastFollowing, "@JillJohnson");
    }

}