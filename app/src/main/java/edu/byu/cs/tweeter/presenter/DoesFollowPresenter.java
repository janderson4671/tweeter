package edu.byu.cs.tweeter.presenter;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.DoesFollowService;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.DoesFollowServiceProxy;

public class DoesFollowPresenter {

    private final View view;

    public interface View {

    }

    public DoesFollowPresenter(View view) {
        this.view = view;
    }

    public DoesFollowResponse doesFollow(DoesFollowRequest request) throws IOException, TweeterRemoteException {
        DoesFollowService service = getDoesFollowService();
        return service.doesFollow(request);
    }

    public DoesFollowService getDoesFollowService() {
        return new DoesFollowServiceProxy();
    }

}
