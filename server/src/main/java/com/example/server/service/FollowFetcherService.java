package com.example.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.dao.FollowDAO;
import com.example.server.model.DBStatus;
import com.example.shared.domain.Status;
import com.example.shared.net.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

public class FollowFetcherService {

    private final int PAGE_SIZE = 25;
    private final String SQS_URL = "https://sqs.us-east-2.amazonaws.com/737937290528/FeedPosterQueue";

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    public String fetchAndPost(String statusStr) {

        Status status = JsonSerializer.deserialize(statusStr, Status.class);

        List<String> followers = getFollowDAO().getAllUsersThatFollow(status.getUser().getAlias());

        List<DBStatus> statusList = new ArrayList<>();

        int pageSize = 0;
        for (String currFollower : followers) {

            if (pageSize == PAGE_SIZE) {
                pushToSQS(statusList);
                pageSize = 0;
                statusList = new ArrayList<>();
            }

            DBStatus currStat = new DBStatus(currFollower, status.getTimeStamp(), status.getMessage(), status.getUser().getAlias());
            statusList.add(currStat);
            pageSize += 1;
        }

        //push to the queue for any leftovers
        pushToSQS(statusList);

        return "Success";

    }

    private void pushToSQS(List<DBStatus> statuses) {
        String messageBody = JsonSerializer.serialize(statuses);

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(SQS_URL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        SendMessageResult result = sqs.sendMessage(request);

    }

}
