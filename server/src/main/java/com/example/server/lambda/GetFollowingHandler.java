package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.GetFollowingServiceImpl;
import com.example.shared.service.request.GetFollowingRequest;
import com.example.shared.service.response.GetFollowingResponse;

public class GetFollowingHandler implements RequestHandler<GetFollowingRequest, GetFollowingResponse> {
    @Override
    public GetFollowingResponse handleRequest(GetFollowingRequest request, Context context) {
        GetFollowingServiceImpl service = new GetFollowingServiceImpl();
        return service.getFollowing(request);
    }
}
