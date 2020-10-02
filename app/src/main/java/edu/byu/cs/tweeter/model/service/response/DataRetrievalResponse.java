package edu.byu.cs.tweeter.model.service.response;

public class DataRetrievalResponse extends Response {

    public DataRetrievalResponse(boolean success) {
        super(success);
    }

    public DataRetrievalResponse(boolean success, String message) {
        super(success, message);
    }
}
