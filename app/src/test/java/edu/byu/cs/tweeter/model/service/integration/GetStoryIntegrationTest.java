package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import edu.byu.cs.tweeter.model.service.GetStoryServiceProxy;

public class GetStoryIntegrationTest {

    GetStoryRequest validRequest;
    GetStoryResponse response;
    User loggedInUser;

    GetStoryServiceProxy service;

    @BeforeEach
    public void setup() {
        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        validRequest = new GetStoryRequest(loggedInUser.getAlias(), new AuthToken(), 12, null);

        service = new GetStoryServiceProxy();
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

        List<Status> story = response.getStatuses();

        Assertions.assertNotNull(story.size());

        if (story.size() > 0) {
            for (Status stat : story) {
                Assertions.assertNotNull(stat.getUser().getAlias());
            }
        }

        validRequest = new GetStoryRequest(loggedInUser.getAlias(), new AuthToken(), 12, story.get(story.size() - 1));

        try {
            response = service.getStatuses(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        story = response.getStatuses();

        Assertions.assertNotNull(story.size());

        if (story.size() > 0) {
            for (Status stat : story) {
                Assertions.assertNotNull(stat.getUser().getAlias());
            }
        }

    }

}
