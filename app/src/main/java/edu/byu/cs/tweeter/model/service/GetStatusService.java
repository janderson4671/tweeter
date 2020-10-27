package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.GetStatusRequest;
import edu.byu.cs.tweeter.model.service.response.GetStatusResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public interface GetStatusService {

    public GetStatusResponse getStatuses(GetStatusRequest request) throws IOException, TweeterRemoteException;

}
