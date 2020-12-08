package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.example.server.service.FeedPosterService;

public class FeedPosterHandler implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent event, Context context) {

        FeedPosterService service = new FeedPosterService();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            try {
                service.postToFeeds(msg.getBody());
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }

        return "Success";
    }
}
