package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowDAO {

    private static final String TableName = "follow";
    private static final String IndexName = "follows-index";

    private static final String UserAttr = "user";
    private static final String FollowsAttr = "follows";

    // DynamoDB client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-east-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    //CRUD Methods
    public static void addFollow(String user, String userToFollow) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(UserAttr, user)
                .withString(FollowsAttr, userToFollow);

        table.putItem(item);
    }

    public static List<String> getFollowing(String user, String lastFollower, int limit) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#user", UserAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(user));

        QueryRequest request = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#user = :user")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (isNonEmptyString(lastFollower)) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(UserAttr, new AttributeValue().withS(user));
            lastKey.put(FollowsAttr, new AttributeValue().withS(lastFollower));

            request = request.withExclusiveStartKey(lastKey);
        }

        QueryResult result = amazonDynamoDB.query(request);
        List<Map<String, AttributeValue>> items = result.getItems();
        List<String> following = new ArrayList<>();

        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                following.add(item.get(FollowsAttr).getS());
            }
        }

        return following;
    }

    public static List<String> getUsersThatFollow(String user, String lastUserToFollow, int limit) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follows", FollowsAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follows", new AttributeValue().withS(user));

        QueryRequest request = new QueryRequest()
                .withTableName(TableName)
                .withIndexName(IndexName)
                .withKeyConditionExpression("#follows = :follows")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (isNonEmptyString(lastUserToFollow)) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(UserAttr, new AttributeValue().withS(user));
            lastKey.put(FollowsAttr, new AttributeValue().withS(lastUserToFollow));

            request = request.withExclusiveStartKey(lastKey);
        }

        QueryResult result = amazonDynamoDB.query(request);
        List<Map<String, AttributeValue>> items = result.getItems();
        List<String> following = new ArrayList<>();

        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                following.add(item.get(UserAttr).getS());
            }
        }

        return following;
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

}
