package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.RegisterService;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterPresenterTest {

    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterService mockRegisterService;
    private RegisterPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        request = new RegisterRequest("Jason", "Anderson", "@jasonanderson",
                "12345678", null);
        response = new RegisterResponse(currentUser, new AuthToken());

        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    public void testRegisterReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.register(request));
    }

    @Test
    public void testRegisterThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterService.register(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.register(request);
        });
    }

}
