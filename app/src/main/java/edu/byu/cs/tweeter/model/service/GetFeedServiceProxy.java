package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import com.example.shared.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.GetFeedRequest;
import com.example.shared.service.response.GetFeedResponse;
import com.example.shared.service.GetFeedService;

import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetFeedServiceProxy implements GetFeedService {

    private static final String URL_PATH_FEED = "/getfeed";

    public GetFeedResponse getStatuses(GetFeedRequest request) throws IOException, TweeterRemoteException {

        String url = null;

        GetFeedResponse response = getServerFacade().getStatuses(request, url);

        if(response.isSuccess()) {
            loadImgaes(response);
        }

        return response;
    }

    private void loadImgaes(GetFeedResponse response) throws IOException {
        for (Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.setUserImage(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}