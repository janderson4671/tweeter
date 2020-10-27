package edu.byu.cs.tweeter.model.net;

import java.util.List;

public class TweeterServerException extends TweeterRemoteException {

    public TweeterServerException(String errorMessage, String errorType, List<String> stackTrace) {
        super(errorMessage, errorType, stackTrace);
    }

}
