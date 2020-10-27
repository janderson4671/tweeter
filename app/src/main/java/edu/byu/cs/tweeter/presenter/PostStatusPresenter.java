package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenter {

    private final View view;

    public interface View {}

    public PostStatusPresenter(View view) { this.view = view; }

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        PostStatusService service = getPostStatusService();
        return service.postStatus(request);
    }

    public PostStatusService getPostStatusService() { return new PostStatusServiceProxy(); }
}
