package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetStatusResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetStatusServiceProxy implements GetStatusService {

    private static final String URL_PATH_FEED = "/getfeed";
    private static final String URL_PATH_STORY = "/getstory";

    public GetStatusResponse getStatuses(GetStatusRequest request) throws IOException, TweeterRemoteException {

        String url = null;

        if (request.getFragmentCode() == 0) {
            url = URL_PATH_FEED;
        } else {
            url = URL_PATH_STORY;
        }

        GetStatusResponse response = getServerFacade().getStatuses(request, url);

        if(response.isSuccess()) {
            loadImgaes(response);
        }

        return response;
    }

    private void loadImgaes(GetStatusResponse response) throws IOException {
        for (Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.setUserImage(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
