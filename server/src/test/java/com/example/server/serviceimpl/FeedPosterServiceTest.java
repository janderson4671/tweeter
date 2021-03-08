package com.example.server.serviceimpl;

import com.example.server.model.DBStatus;
import com.example.server.service.FeedPosterService;
import com.example.server.service.LoginServiceImpl;
import com.example.server.service.LogoutServiceImpl;
import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.JsonSerializer;
import com.example.shared.service.LoginService;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FeedPosterServiceTest {

    private FeedPosterService service;

    static User loggedInUser;
    static AuthToken authToken;

    List<DBStatus> statusList;

    @BeforeAll
    static void logInUser() {
        LoginRequest request = new LoginRequest("@person198", "password");
        LoginService service = new LoginServiceImpl();
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
        LogoutService service = new LogoutServiceImpl();

        try {
            service.logout(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @BeforeEach
    public void setup() {

        service = new FeedPosterService();

        statusList = new ArrayList<>();

        DBStatus stat1 = new DBStatus("@person200", "1", "Hi there", loggedInUser.getAlias());
        DBStatus stat2 = new DBStatus("@person201", "1", "Hi there", loggedInUser.getAlias());
        DBStatus stat3 = new DBStatus("@person202", "1", "Hi there", loggedInUser.getAlias());

        statusList.add(stat1);
        statusList.add(stat2);
        statusList.add(stat3);
    }

    @Test
    public void testFollowFetch() {

        try {
            String statusStr = JsonSerializer.serialize(statusList);

            String message = service.postToFeeds(statusStr);

            Assertions.assertNotNull(message);
            Assertions.assertEquals(message, "Success");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }


    }

}
