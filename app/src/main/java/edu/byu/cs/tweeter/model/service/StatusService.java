package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class StatusService {

    public StatusResponse getStatuses(StatusRequest request) throws IOException {
        return getServerFacade().getFeed(request);
    }

//    private void loadImgaes(StatusResponse response) throws IOException {
//        for (Status status : response.getStatuses()) {
//            byte [] bytes = ByteArrayUtils.bytesFromUrl()
//        }
//    }

    private ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
