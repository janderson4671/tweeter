package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStatusRequest extends DataRetrievalRequest {

    private Status lastStatus;

    public GetStatusRequest(User user, AuthToken authToken, int limit, Status lastStatus, int fragmentCode) {
        super(user, authToken, limit, fragmentCode);

        this.lastStatus = lastStatus;
    }

    public Status getLastStatus() {
        return lastStatus;
    }
}