package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenter {

    private final View view;

    public interface View {}

    public PostStatusPresenter(View view) { this.view = view; }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        PostStatusService service = getPostStatusService();
        return service.addPost(request);
    }

    private PostStatusService getPostStatusService() { return new PostStatusService(); }
}
