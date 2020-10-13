package edu.byu.cs.tweeter.model.service.response;

/**
 * A base class for server responses.
 */
public class Response {

    private final boolean success;
    private final String message;

    Response(boolean success) {
        this(success, null);
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
