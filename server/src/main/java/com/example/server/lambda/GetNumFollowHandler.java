package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.GetNumFollowServiceImpl;
import com.example.shared.service.request.GetNumFollowRequest;
import com.example.shared.service.response.GetNumFollowResponse;

public class GetNumFollowHandler implements RequestHandler<GetNumFollowRequest, GetNumFollowResponse> {

    @Override
    public GetNumFollowResponse handleRequest(GetNumFollowRequest request, Context context) {
        GetNumFollowServiceImpl service = new GetNumFollowServiceImpl();
        return service.getNumFollow(request);
    }
}
