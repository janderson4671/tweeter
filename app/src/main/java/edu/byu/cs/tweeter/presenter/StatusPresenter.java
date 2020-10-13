package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class StatusPresenter {

    private final View view;

    public interface View {

    }

    public StatusPresenter(View view) {
        this.view = view;
    }

    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        StatusService statusService = getStatusService();
        return statusService.getStatuses(request);
    }

    public StatusService getStatusService() {
        return new StatusService();
    }
}
