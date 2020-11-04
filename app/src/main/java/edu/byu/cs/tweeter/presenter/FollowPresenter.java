package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.service.FollowService;
import edu.byu.cs.tweeter.model.service.FollowServiceProxy;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowPresenter {

    private final View view;

    public interface View {

    }

    public FollowPresenter(View view) {
        this.view = view;
    }

    public FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException {
        FollowService service = getAddFollowerService();
        return service.follow(request);
    }

    public FollowService getAddFollowerService() {
        return new FollowServiceProxy();
    }

}
