package edu.byu.cs.tweeter.model.service;

import com.example.shared.domain.Status;
import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.GetStoryService;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class GetStoryServiceProxy implements GetStoryService {

    private static final String URL_PATH_STORY = "/getstory";

    private void loadImgaes(GetStoryResponse response) throws IOException {
        for (Status status : response.getStatuses()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(status.getUser().getImageUrl());
            status.setUserImage(bytes);
        }
    }

    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

    @Override
    public GetStoryResponse getStatuses(GetStoryRequest request) throws IOException, TweeterRemoteException {

        GetStoryResponse response = getServerFacade().getStory(request, URL_PATH_STORY);

        if (response.isSuccess()) {
            loadImgaes(response);
        }

        return response;
    }
}
