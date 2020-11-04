package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.RegisterService;
import edu.byu.cs.tweeter.model.service.RegisterServiceProxy;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public class RegisterPresenter {
    private final RegisterPresenter.View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public RegisterPresenter(RegisterPresenter.View view) {
        this.view = view;
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws IOException, TweeterRemoteException {
        RegisterService registerService = getRegisterService();
        return registerService.register(registerRequest);
    }

    public RegisterService getRegisterService() {
        return new RegisterServiceProxy();
    }
}
