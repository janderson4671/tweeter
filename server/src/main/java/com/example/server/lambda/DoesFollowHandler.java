package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.DoesFollowServiceImpl;
import com.example.shared.service.request.DoesFollowRequest;
import com.example.shared.service.response.DoesFollowResponse;

public class DoesFollowHandler implements RequestHandler<DoesFollowRequest, DoesFollowResponse> {
    @Override
    public DoesFollowResponse handleRequest(DoesFollowRequest request, Context context) {
        DoesFollowServiceImpl service = new DoesFollowServiceImpl();
        return service.doesFollow(request);
    }
}
