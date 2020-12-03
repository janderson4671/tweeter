package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.example.shared.domain.AuthToken;

import java.time.LocalDateTime;
public class AuthTokenDAO {

    private static int SESSION_TIMEOUT_MINS = 5;

    private static final String TableName = "auth-token";

    //Attribute Names
    private static final String UUIDAttr = "uuid";
    private static final String ExpirationDateAttr = "expiration-date";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-east-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    //Table Creation

    //CRUD Methods
    public static void createSession(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(UUIDAttr, authToken.getToken())
                .withString(ExpirationDateAttr, authToken.getExpirationDate());

        table.putItem(item);
    }

    public static void updateSession(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(UUIDAttr, authToken.getToken())
                .withUpdateExpression("set expiration-date = :d")
                .withValueMap(new ValueMap().withString(ExpirationDateAttr, authToken.getExpirationDate()))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception ex) {
            System.out.println("Unable to update AuthToken");
            System.out.println(ex.getMessage());
        }
    }

    public static boolean validateUser(AuthToken token) {
        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(UUIDAttr, token.getToken());

        AuthToken authToken = new AuthToken(item.getString(UUIDAttr), item.getString(ExpirationDateAttr));

        //Test the expiration date of the authToken
        LocalDateTime currTime = LocalDateTime.now();
        LocalDateTime expirationTime = LocalDateTime.parse(authToken.getExpirationDate());

        if (currTime.isAfter(expirationTime)) {
            return false;
        }
        else {
            authToken.setExpirationDate(currTime.plusMinutes(SESSION_TIMEOUT_MINS).toString());
            updateSession(authToken);
            return true;
        }
    }

    public static void destroySession(AuthToken token) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(UUIDAttr, token.getToken());
    }

}
