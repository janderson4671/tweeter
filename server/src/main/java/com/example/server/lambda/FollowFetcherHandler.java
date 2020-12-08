package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowFetcherService;

public class FollowFetcherHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String statusStr, Context context) {

        FollowFetcherService service = new FollowFetcherService();

        try {
            service.fetchAndPost(statusStr);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return "Success";
    }
}
