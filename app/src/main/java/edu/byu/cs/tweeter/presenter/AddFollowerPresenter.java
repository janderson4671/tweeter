package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.AddFollowerService;
import edu.byu.cs.tweeter.model.service.request.AddFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.Response;

public class AddFollowerPresenter {

    private final View view;

    public interface View {

    }

    public AddFollowerPresenter(View view) {
        this.view = view;
    }

    public AddFollowerResponse addFollower(AddFollowerRequest request) throws IOException {
        AddFollowerService service = getAddFollowerService();
        return service.addFollower(request);
    }

    public AddFollowerService getAddFollowerService() {
        return new AddFollowerService();
    }

}
