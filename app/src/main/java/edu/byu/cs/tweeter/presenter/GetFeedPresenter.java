package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetFeedService;
import edu.byu.cs.tweeter.model.service.GetFeedServiceProxy;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;

public class GetFeedPresenter {

    private final View view;

    public interface View {

    }

    public GetFeedPresenter(View view) {
        this.view = view;
    }

    public GetFeedResponse getStatuses(GetFeedRequest request) throws IOException, TweeterRemoteException {
        GetFeedService getFeedService = getStatusService();
        return getFeedService.getStatuses(request);
    }

    public GetFeedService getStatusService() {
        return new GetFeedServiceProxy();
    }
}
