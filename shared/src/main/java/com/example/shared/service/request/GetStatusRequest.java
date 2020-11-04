package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;

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
