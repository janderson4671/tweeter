package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.LoginService;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

public class LoginPresenterTest {

    private LoginRequest request;
    private LoginResponse response;
    private LoginService mockLoginService;
    private LoginPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        request = new LoginRequest(currentUser.getAlias(), "12345678");
        response = new LoginResponse(currentUser, new AuthToken());

        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void testLoginReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testLoginThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.login(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.login(request);
        });
    }
}
