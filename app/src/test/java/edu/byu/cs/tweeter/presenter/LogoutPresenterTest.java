package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.LogoutService;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public class LogoutPresenterTest {

    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, 0, 0);

        request = new LogoutRequest(currentUser.getAlias(), new AuthToken());
        response = new LogoutResponse(true, "Logout successfull");

        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testLogoutReturnsResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testLogoutThrowsException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());

        Assertions.assertNotNull(response);
        Assertions.assertThrows(IOException.class, () -> {
            presenter.logout(request);
        });
    }
}
