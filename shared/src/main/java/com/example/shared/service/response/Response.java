package com.example.shared.service.response;

/**
 * A base class for server responses.
 */
public class Response {

    private boolean success;
    private String message;

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

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
