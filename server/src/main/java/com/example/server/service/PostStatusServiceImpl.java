package com.example.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.dao.AuthTokenDAO;
import com.example.server.dao.StoryDAO;
import com.example.shared.domain.Status;
import com.example.shared.net.JsonSerializer;
import com.example.shared.service.PostStatusService;
import com.example.shared.service.request.PostStatusRequest;
import com.example.shared.service.response.PostStatusResponse;

public class PostStatusServiceImpl implements PostStatusService {

    private final String SQS_URL = "https://sqs.us-east-2.amazonaws.com/737937290528/FollowFetcherQueue";

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {

        //Authenticate the user
        if (!getAuthTokenDAO().validateUser(request.getAuthToken())) {
            return new PostStatusResponse(false, "User Session Timed Out");
        }

        try {
            getStoryDAO().addPost(request.getUser(), request.getStatus());
        } catch (Exception ex) {
            return new PostStatusResponse(false, ex.getMessage());
        }

        //Push the status to a SQS
        pushToSQS(request.getStatus());

        return new PostStatusResponse(true, "Added Post");
    }


    public void pushToSQS(Status status) {
        String messageBody = JsonSerializer.serialize(status);

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(SQS_URL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        SendMessageResult result = sqs.sendMessage(request);
    }
}
