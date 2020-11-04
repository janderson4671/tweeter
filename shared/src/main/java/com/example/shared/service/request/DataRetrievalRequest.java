package com.example.shared.service.request;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class DataRetrievalRequest {

    private final User user;
    private final int limit;
    private final int fragmentCode;

    private AuthToken authToken;

    public DataRetrievalRequest(User user, AuthToken authToken, int limit, int fragmentCode) {
        this.user = user;
        this.limit = limit;
        this.fragmentCode = fragmentCode;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public int getFragmentCode() {
        return fragmentCode;
    }

}
