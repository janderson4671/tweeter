package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.FollowServiceImpl;
import com.example.shared.service.request.FollowRequest;
import com.example.shared.service.response.FollowResponse;

public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.follow(request);
    }
}
