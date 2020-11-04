package com.example.shared.service;

import java.io.IOException;

import com.example.shared.net.TweeterRemoteException;
import com.example.shared.service.request.LogoutRequest;
import com.example.shared.service.response.LogoutResponse;

public interface LogoutService {

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException;

}
