package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

/**
 * Contains the business logic to support the login operation.
 */
public interface LoginService {
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}
