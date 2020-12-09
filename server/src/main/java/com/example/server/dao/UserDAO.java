package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.example.shared.domain.User;
import com.example.shared.net.TweeterRemoteException;

import java.util.ArrayList;

public class UserDAO {

    private static final String TableName = "user";

    //Attribute Names
    private static final String FirstNameAttr = "first-name";
    private static final String LastNameAttr = "last-name";
    private static final String AliasAttr = "alias";
    private static final String ImageAttr = "profile-image";
    private static final String NumFollowers = "num-followers";
    private static final String NumFollowing = "num-following";
    private static final String PasswordAttr = "password";

    //AWS Client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                                                        .standard()
                                                        .withRegion("us-east-2")
                                                        .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    //CRUD Methods
    public void addUser(User user, String password) {
        Table table = dynamoDB.getTable(TableName);

        //See if user already exists
        User tempUser = getUser(user.getAlias());

        //If they don't exist, then we add them to the Dynamo Table
        Item item = new Item()
                .withPrimaryKey(AliasAttr, user.getAlias())
                .withString(FirstNameAttr, user.getFirstName())
                .withString(LastNameAttr, user.getLastName())
                .withString(ImageAttr, user.getImageUrl())
                .withString(PasswordAttr, password)
                .withNumber(NumFollowers, user.getNumFollowers())
                .withNumber(NumFollowing, user.getNumFollowing());

        table.putItem(item);
    }

    public void updateFollowing(String userAlias, boolean add) {
        Table table = dynamoDB.getTable(TableName);

        int numFollowing = add ? 1 : -1;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(AliasAttr, userAlias)
                .withAttributeUpdate(new AttributeUpdate(NumFollowing).addNumeric(numFollowing));

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception ex) {
            System.out.println("Unable to update Num-Following");
            System.out.println(ex.getMessage());
        }
    }

    public void updateFollower(String userAlias, boolean add) {
        Table table = dynamoDB.getTable(TableName);

        int numFollowers = add ? 1 : -1;

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(AliasAttr, userAlias)
                .withAttributeUpdate(new AttributeUpdate(NumFollowers).addNumeric(numFollowers));

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception ex) {
            System.out.println("Unable to update Num-Followers");
            System.out.println(ex.getMessage());
        }
    }

    //Get Methods
    public User getUser(String userAlias) {

        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, userAlias);

        if (item == null) {
            return null;
        }

        User resultUser = new User(item.getString(FirstNameAttr), item.getString(LastNameAttr), item.getString(AliasAttr),
                item.getString(ImageAttr), item.getInt(NumFollowers), item.getInt(NumFollowing));

        return resultUser;
    }

    public String getUserPassword(String userAlias) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(AliasAttr, userAlias);

        if (item == null) {
            return null;
        }

        return item.getString(PasswordAttr);
    }

    public boolean correctPassword(String userAlias, String password) {
        String userPassword = getUserPassword(userAlias);

        return (userPassword.equals(password));
    }

}
