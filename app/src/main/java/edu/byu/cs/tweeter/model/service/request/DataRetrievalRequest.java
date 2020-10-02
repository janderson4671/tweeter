package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class DataRetrievalRequest {

    private final User user;
    private final int limit;
    private final int fragmentCode;

    private final Object lastElement;

    public DataRetrievalRequest(User user, int limit, int fragmentCode, Object lastElement) {
        this.user = user;
        this.limit = limit;
        this.fragmentCode = fragmentCode;
        this.lastElement = lastElement;
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

    public Object getLastElement() {
        return lastElement;
    }

}
