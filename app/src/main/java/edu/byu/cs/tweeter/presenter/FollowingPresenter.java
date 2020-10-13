package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowingPresenter {

    private final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public FollowingPresenter(View view) {
        this.view = view;
    }

    public FollowResponse getFollowing(FollowRequest request) throws IOException {
        FollowingService followingService = getFollowingService();
        return followingService.getFollowees(request);
    }

    FollowingService getFollowingService() {
        return new FollowingService();
    }
}
