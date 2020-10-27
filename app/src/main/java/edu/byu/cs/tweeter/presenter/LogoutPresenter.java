package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutPresenter {

    private final View view;

    public interface View {}

    public LogoutPresenter(View view) { this.view = view; }

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        LogoutService service = getLogoutService();
        return service.logout(request);
    }

    public LogoutService getLogoutService() {
        return new LogoutServiceProxy();
    }
}
