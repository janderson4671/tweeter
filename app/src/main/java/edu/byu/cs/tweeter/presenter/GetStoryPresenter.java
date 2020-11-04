package edu.byu.cs.tweeter.presenter;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetStoryService;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.GetStoryServiceProxy;

public class GetStoryPresenter {

    private final GetStoryPresenter.View view;

    public interface View {

    }

    public GetStoryPresenter(GetStoryPresenter.View view) {
        this.view = view;
    }

    public GetStoryResponse getStatuses(GetStoryRequest request) throws IOException, TweeterRemoteException {
        GetStoryService getstoryService = getStatusService();
        return getstoryService.getStatuses(request);
    }

    public GetStoryService getStatusService() {
        return new GetStoryServiceProxy();
    }

}
