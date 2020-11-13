package edu.byu.cs.tweeter.presenter;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetNumFollowService;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.GetNumFollowServiceProxy;

public class GetNumFollowPresenter {

    private final View view;

    public interface View {

    }

    public GetNumFollowPresenter(View view) { this.view = view; }

    public GetNumFollowResponse getNumFollow(GetNumFollowRequest request) throws IOException, TweeterRemoteException {
        GetNumFollowService service = getNumFollowService();
        return service.getNumFollow(request);
    }

    public GetNumFollowService getNumFollowService() { return new GetNumFollowServiceProxy(); }

}
