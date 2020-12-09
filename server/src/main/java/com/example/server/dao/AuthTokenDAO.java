package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
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

    private static int SESSION_TIMEOUT_MINS = 30;

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
    public void createSession(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(UUIDAttr, authToken.getToken())
                .withString(ExpirationDateAttr, authToken.getExpirationDate());

        table.putItem(item);
    }

    public void updateSession(AuthToken authToken) {
        Table table = dynamoDB.getTable(TableName);



        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(UUIDAttr, authToken.getToken())
                .withAttributeUpdate(new AttributeUpdate(ExpirationDateAttr).put(authToken.getExpirationDate()));

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception ex) {
            System.out.println("Unable to update AuthToken");
            System.out.println(ex.getMessage());
        }
    }

    public boolean validateUser(AuthToken token) {

        try {
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public void destroySession(AuthToken token) {
        Table table = dynamoDB.getTable(TableName);
        table.deleteItem(UUIDAttr, token.getToken());
    }

}
