package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.GetStatusService;
import edu.byu.cs.tweeter.model.service.GetStatusServiceProxy;
import edu.byu.cs.tweeter.model.service.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetStatusResponse;

public class GetStatusPresenter {

    private final View view;

    public interface View {

    }

    public GetStatusPresenter(View view) {
        this.view = view;
    }

    public GetStatusResponse getStatuses(GetStatusRequest request) throws IOException, TweeterRemoteException {
        GetStatusService getStatusService = getStatusService();
        return getStatusService.getStatuses(request);
    }

    public GetStatusService getStatusService() {
        return new GetStatusServiceProxy();
    }
}
