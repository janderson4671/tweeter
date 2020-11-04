package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.RegisterRequest;
import com.example.shared.service.response.RegisterResponse;

public interface RegisterService {
    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException;
}
