package com.example.shared.net;

import java.util.List;

public class TweeterRemoteException extends Exception {

    String errorMessage;
    String errorType;
    List<String> stackTrace;

    public TweeterRemoteException(String errorMessage, String errorType, List<String> stackTrace) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.stackTrace = stackTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public List<String> getTrace() {
        return stackTrace;
    }
}
