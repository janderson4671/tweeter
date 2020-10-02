package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class DataRetrievalRequest {

    private final User user;
    private final int limit;
    private final int fragmentType;

    public DataRetrievalRequest(User user, int limit, int fragmentType) {
        this.user = user;
        this.limit = limit;
        this.fragmentType = fragmentType;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public int getFragmentType() {
        return fragmentType;
    }


}
