package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterServiceProxyTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterServiceProxy registerServiceSpy;

    private String dummyURL = "/helloworld";

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        // Setup request objects to use in the tests
        validRequest = new RegisterRequest("Jason", "Anderson", "@jasonAnderson", "12345678", null);
        invalidRequest = new RegisterRequest("Nathan", "Craddock", "@merp", "12348765", null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(currentUser, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest, dummyURL)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occured");
        Mockito.when(mockServerFacade.register(invalidRequest, dummyURL)).thenReturn(failureResponse);

        // Create a RegisteringService instance and wrap it with a spy that will use the mock service
        registerServiceSpy = Mockito.spy(new RegisterServiceProxy());
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.getServerFacade().register(validRequest, dummyURL);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRegister_validRequest_correctAlias() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.getServerFacade().register(validRequest, dummyURL);

        Assertions.assertNotNull(response.getUser().getAlias());
        Assertions.assertEquals("@FirstNameLastName", response.getUser().getAlias());
    }

    @Test
    public void testGetRegisterees_invalidRequest_returnsNoRegisterees() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceSpy.getServerFacade().register(invalidRequest, dummyURL);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(failureResponse, response);
    }

}
