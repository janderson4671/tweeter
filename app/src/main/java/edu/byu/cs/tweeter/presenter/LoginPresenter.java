package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.LoginService;
import edu.byu.cs.tweeter.model.service.LoginServiceProxy;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public LoginPresenter(View view) {
        this.view = view;
    }

    public LoginResponse login(LoginRequest loginRequest) throws IOException, TweeterRemoteException {
        LoginService loginService = getLoginService();
        return loginService.login(loginRequest);
    }

    public LoginService getLoginService() {
        return new LoginServiceProxy();
    }
}
