package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.LoginServiceProxy;

public class LoginIntegrationTest {

    LoginRequest validRequest;
    LoginServiceProxy service;

    LoginResponse response;

    String username = "Jason";
    String password = "12345678";

    @BeforeEach
    public void setup() {

        validRequest = new LoginRequest(username, password);

        service = new LoginServiceProxy();
    }

    @Test
    public void test_validRequest() {

        try {
            response = service.login(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getUser().getAlias());
        Assertions.assertNotNull(response.getUser().getLastName());

    }

}
