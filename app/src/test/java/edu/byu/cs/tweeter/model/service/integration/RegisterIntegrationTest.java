package edu.byu.cs.tweeter.model.service.integration;

import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.service.RegisterServiceProxy;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class RegisterIntegrationTest {

    RegisterRequest validRequest;
    RegisterServiceProxy service;

    RegisterResponse response;

    String username = "@test";
    String password = "password";
    String firstName = "BlahBlah";
    String lastName = "halbhalb";

    @BeforeEach
    public void setup() {

        byte [] profile;

        try {
            profile = ByteArrayUtils.bytesFromUrl("https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40blah");
            validRequest = new RegisterRequest(firstName, lastName, username, password, profile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }



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
        Assertions.assertEquals(response.getUser().getAlias(), "@test");
        Assertions.assertEquals(response.getUser().getLastName(), "halbhalb");
        Assertions.assertTrue(response.isSuccess());

    }

}
