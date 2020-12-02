package com.example.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
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

    private static boolean isNotEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    //Table Creation
    public void createTable() throws TweeterRemoteException {
        try {
            ArrayList<AttributeDefinition> tableAttributeDefinitions = new ArrayList<>();
            tableAttributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName(AliasAttr)
                    .withAttributeType("S"));

            //Table key schema
            ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
            tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(AliasAttr)
                .withKeyType(KeyType.HASH)); //Partition key

            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName(TableName)
                    .withAttributeDefinitions(tableAttributeDefinitions)
                    .withKeySchema(tableKeySchema);

            Table table = dynamoDB.createTable(createTableRequest);
            table.waitForActive();
        } catch (Exception ex) {
            throw new TweeterRemoteException("Error Creating Dynamo Table", "AWS", null);
        }
    }

    public void deleteTable() throws TweeterRemoteException {
        try {
            Table table = dynamoDB.getTable(TableName);
            if (table != null) {
                table.delete();
                table.waitForDelete();
            }
        } catch (Exception e) {
            throw new TweeterRemoteException("Error Deleting Dynamo Table", "AWS", null);
        }
    }

    //CRUD Methods
    public static void addUser(User user, String password) {
        Table table = dynamoDB.getTable(TableName);

        //See if user already exists
        User tempUser = getUser(user.getAlias());

        if (tempUser != null) {
            //TODO: Throw an error here?
            return;
        }

        //If they don't exist, then we add them to the Dynamo Table
        Item item = new Item()
                .withPrimaryKey(AliasAttr, user.getAlias())
                .withString(FirstNameAttr, user.getFirstName())
                .withString(LastNameAttr, user.getLastName())
                .withString(ImageAttr, user.getImageUrl())
                .withString(PasswordAttr, password)
                .withNumber(NumFollowers, 0)
                .withNumber(NumFollowing, 0);

        //TODO: add hashed password here
        table.putItem(item);
    }

    //Get Methods
    public static User getUser(String userAlias) {

        Table table = dynamoDB.getTable(TableName);

        Item item = table.getItem(AliasAttr, userAlias);

        if (item == null) {
            return null;
        }

        User resultUser = new User(item.getString(FirstNameAttr), item.getString(LastNameAttr), item.getString(AliasAttr),
                item.getString(ImageAttr), item.getInt(NumFollowers), item.getInt(NumFollowing));

        return resultUser;
    }

    public static String getUserPassword(String userAlias) {
        Table table = dynamoDB.getTable(TableName);
        Item item = table.getItem(AliasAttr, userAlias);

        if (item == null) {
            return null;
        }

        return item.getString(PasswordAttr);
    }

    public static boolean correctPassword(String userAlias, String password) {
        String userPassword = getUserPassword(userAlias);

        return (userPassword.equals(password));
    }

}
