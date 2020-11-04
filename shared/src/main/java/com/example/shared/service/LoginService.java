package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LoginRequest;
import com.example.shared.service.response.LoginResponse;

/**
 * Contains the business logic to support the login operation.
 */
public interface LoginService {
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}
