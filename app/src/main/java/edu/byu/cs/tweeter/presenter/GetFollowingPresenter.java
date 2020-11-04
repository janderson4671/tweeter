package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFollowingService;
import edu.byu.cs.tweeter.model.service.GetFollowingServiceProxy;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class GetFollowingPresenter {

    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public GetFollowingPresenter(View view) {
        this.view = view;
    }

    public GetFollowingResponse getFollowing(GetFollowingRequest request) throws IOException, TweeterRemoteException {
        GetFollowingService getFollowingService = getFollowingService();
        return getFollowingService.getFollowing(request);
    }

    GetFollowingService getFollowingService() {
        return new GetFollowingServiceProxy();
    }
}
