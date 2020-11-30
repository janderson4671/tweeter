package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.RegisterServiceProxy;

public class RegisterIntegrationTest {

    RegisterRequest validRequest;
    RegisterServiceProxy service;

    RegisterResponse response;

    String username = "Jason";
    String password = "12345678";
    String firstName = "BlahBlah";
    String lastName = "halbhalb";

    @BeforeEach
    public void setup() {

        validRequest = new RegisterRequest(firstName, lastName, username, password, null);

        service = new RegisterServiceProxy();
    }

    @Test
    public void test_validRequest() {

        try {
            response = service.register(validRequest);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getUser().getAlias(), "@BlahBlahhalbhalb");
        Assertions.assertEquals(response.getUser().getLastName(), "halbhalb");
        Assertions.assertTrue(response.isSuccess());

    }

}
