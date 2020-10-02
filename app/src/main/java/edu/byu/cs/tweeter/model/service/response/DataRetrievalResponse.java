package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class DataRetrievalResponse extends PagedResponse {

    List<User> data;

    public DataRetrievalResponse(String message) {
        super(false, message, false);
    }

    public DataRetrievalResponse(List<User> data, boolean hasMorePages) {
        super(true, hasMorePages);
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }
}
