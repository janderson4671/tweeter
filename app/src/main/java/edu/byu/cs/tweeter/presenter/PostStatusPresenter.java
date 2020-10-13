package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenter {

    private final View view;

    public interface View {}

    public PostStatusPresenter(View view) { this.view = view; }

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException {
        PostStatusService service = getPostStatusService();
        return service.addPost(request);
    }

    public PostStatusService getPostStatusService() { return new PostStatusService(); }
}
