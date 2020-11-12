package com.example.server.dao;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GetFeedDAOTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    private final User user = new User("Bob", "Bobson", MALE_IMAGE_URL, 0, 0);
    private final Status stat = new Status(user, "test", "Fake Date", new ArrayList<>());
    private final int limit = 12;

    GetFeedDAO dao;

    GetFeedRequest validRequest;

    @BeforeEach
    public void setup() {
        dao = new GetFeedDAO();
        validRequest = new GetFeedRequest(user.getAlias(), new AuthToken(), limit, null);
    }

    @Test
    public void testPaginatedFeed() {

        GetFeedResponse response = dao.getStatuses(validRequest);

        Assertions.assertNotNull(response.getStatuses());
        Assertions.assertEquals(response.getStatuses().get(response.getStatuses().size() - 1).getUser().getAlias(), "@FranFranklin");
        Assertions.assertTrue(response.getHasMorePages());

        Status lastStatus = response.getStatuses().get(response.getStatuses().size() - 1);

        validRequest = new GetFeedRequest(user.getAlias(), new AuthToken(), limit, lastStatus);

        response = dao.getStatuses(validRequest);

        Assertions.assertNotNull(response.getStatuses());
        Assertions.assertFalse(response.getHasMorePages());
        Assertions.assertEquals(response.getStatuses().get(response.getStatuses().size() - 1).getUser().getAlias(), "@JillJohnson");

    }
}
