package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.model.DBStatus;
import com.example.server.service.FeedPosterService;
import com.example.shared.net.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FeedPosterHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String listStatusStr, Context context) {

        FeedPosterService service = new FeedPosterService();

        try {
            service.postToFeeds(listStatusStr);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return "Success";
    }
}
