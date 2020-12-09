package edu.byu.cs.tweeter.model.service.integration;

import android.icu.text.SymbolTable;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.LoginResponse;
import com.example.shared.service.response.PostStatusResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;
import edu.byu.cs.tweeter.model.service.PostStatusServiceProxy;

public class PostStatusIntegrationTest {

    PostStatusRequest validRequest;
    PostStatusResponse response;
    static User loggedInUser;
    static AuthToken authToken;
    Status status;

    PostStatusServiceProxy service;

    @BeforeAll
    static void logInUser() {
        LoginRequest request = new LoginRequest("@person198", "password");
        LoginServiceProxy service = new LoginServiceProxy();
        LoginResponse response;

        try {
            response = service.login(request);
            loggedInUser = response.getUser();
            authToken = response.getAuthToken();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @AfterAll
    static void cleanUp() {
        LogoutRequest request = new LogoutRequest(loggedInUser.getAlias(), authToken);
        LogoutServiceProxy service = new LogoutServiceProxy();

        try {
            service.logout(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @BeforeEach
    public void setup() {

        status = new Status(loggedInUser, "test", "Fake Date", new ArrayList<>());

        validRequest = new PostStatusRequest(status, loggedInUser.getAlias(), authToken);

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
