package edu.byu.cs.tweeter.model.net;

import java.util.List;

public class TweeterRequestException extends TweeterRemoteException {

    public TweeterRequestException(String errorMessage, String errorType, List<String> stackTrace) {
        super(errorMessage, errorType, stackTrace);
    }

}
