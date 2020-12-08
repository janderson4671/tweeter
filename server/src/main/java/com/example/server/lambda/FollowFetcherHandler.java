package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.example.server.service.FollowFetcherService;

public class FollowFetcherHandler implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent event, Context context) {

        FollowFetcherService service = new FollowFetcherService();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {

            try {
                service.fetchAndPost(msg.getBody());
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }

        }
        return "Success";
    }
}
