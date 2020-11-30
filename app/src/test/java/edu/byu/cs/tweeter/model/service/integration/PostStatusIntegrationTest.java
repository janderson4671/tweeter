package edu.byu.cs.tweeter.model.service.integration;

import android.icu.text.SymbolTable;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.service.PostStatusServiceProxy;

public class PostStatusIntegrationTest {

    PostStatusRequest validRequest;
    PostStatusResponse response;
    User loggedInUser;
    Status status;

    PostStatusServiceProxy service;

    @BeforeEach
    public void setup() {
        loggedInUser = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0, 0);

        status = new Status(loggedInUser, "test", "Fake Date", new ArrayList<>());

        validRequest = new PostStatusRequest(status, loggedInUser.getAlias(), new AuthToken());

        service = new PostStatusServiceProxy();
    }

    @Test
    public void test_validRequest() {
        try {
            response = service.postStatus(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

}
