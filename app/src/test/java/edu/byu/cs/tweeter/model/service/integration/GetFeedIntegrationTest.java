package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.model.service.GetFeedServiceProxy;

public class GetFeedIntegrationTest {

    GetFeedRequest validRequest;
    GetFeedResponse response;
    User loggedInUser;

    GetFeedServiceProxy service;

    @BeforeEach
    public void setup() {
        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new GetFeedRequest(loggedInUser.getAlias(), new AuthToken(), 12, null);

        service = new GetFeedServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.getStatuses(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);

        List<Status> feed = response.getStatuses();

        Assertions.assertNotNull(feed.size());

        if (feed.size() > 0) {
            for (Status stat : feed) {
                Assertions.assertNotNull(stat.getUser().getAlias());
            }
        }

        validRequest = new GetFeedRequest(loggedInUser.getAlias(), new AuthToken(), 12, feed.get(feed.size() - 1));

        try {
            response = service.getStatuses(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        feed = response.getStatuses();

        Assertions.assertNotNull(feed.size());

        if (feed.size() > 0) {
            for (Status stat : feed) {
                Assertions.assertNotNull(stat.getUser().getAlias());
            }
        }

    }

}
