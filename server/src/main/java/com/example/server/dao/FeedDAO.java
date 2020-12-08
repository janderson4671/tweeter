package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.example.server.model.DBStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedDAO {

    private static final String TableName = "feed";

    //Attributes
    private static final String UserAttr = "user";
    private static final String TimeAttr = "time-stamp";
    private static final String MessageAttr = "message";
    private static final String AuthorAttr = "author";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-east-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    //CRUD Methods

    public static List<DBStatus> getFeed(String userAlias, String lastStatusTime, int limit) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#user", UserAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(userAlias));

        QueryRequest request = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#user = :user")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withScanIndexForward(false)
                .withLimit(limit);

        if (isNonEmptyString(lastStatusTime)) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(UserAttr, new AttributeValue().withS(userAlias));
            lastKey.put(TimeAttr, new AttributeValue().withS(lastStatusTime));

            request = request.withExclusiveStartKey(lastKey);
        }

        QueryResult result = amazonDynamoDB.query(request);
        List<Map<String, AttributeValue>> items = result.getItems();

        List<DBStatus> statuses = new ArrayList<>();

        for (Map<String, AttributeValue> item : items) {
            DBStatus currStatus = new DBStatus(item.get(UserAttr).getS(), item.get(TimeAttr).getS(), item.get(MessageAttr).getS(), item.get(AuthorAttr).getS());
            statuses.add(currStatus);
        }

        return statuses;
    }

    public static void batchPost(List<DBStatus> statuses) {

        TableWriteItems items = new TableWriteItems(TableName);

        for (DBStatus status : statuses) {
            Item item = new Item()
                    .withPrimaryKey(UserAttr, status.getUserAlias())
                    .withString(TimeAttr, status.getTimeStamp())
                    .withString(MessageAttr, status.getMessage())
                    .withString(AuthorAttr, status.getAuthor());

            items.addItemToPut(item);

            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(TableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }

    }

    private static void loopBatchWrite(TableWriteItems items) {
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
        }
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

}
