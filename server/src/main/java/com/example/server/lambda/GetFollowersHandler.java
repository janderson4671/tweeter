package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.GetFollowersServiceImpl;
import com.example.shared.service.request.GetFollowersRequest;
import com.example.shared.service.response.GetFollowersResponse;

public class GetFollowersHandler implements RequestHandler<GetFollowersRequest, GetFollowersResponse> {
    @Override
    public GetFollowersResponse handleRequest(GetFollowersRequest request, Context context) {
        GetFollowersServiceImpl service = new GetFollowersServiceImpl();
        return service.getFollowers(request);
    }
}
