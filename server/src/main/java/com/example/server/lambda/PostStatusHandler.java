package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.PostStatusServiceImpl;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {

    public PostStatusResponse handleRequest(PostStatusRequest request, Context context) {

        PostStatusServiceImpl service = new PostStatusServiceImpl();
        return service.postStatus(request);

    }
}
