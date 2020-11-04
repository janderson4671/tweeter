package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.GetStoryServiceImpl;
import com.example.shared.service.request.GetStoryRequest;
import com.example.shared.service.response.GetStoryResponse;

public class GetStoryHandler implements RequestHandler<GetStoryRequest, GetStoryResponse> {
    @Override
    public GetStoryResponse handleRequest(GetStoryRequest request, Context context) {
        GetStoryServiceImpl service = new GetStoryServiceImpl();
        return service.getStatuses(request);
    }
}
