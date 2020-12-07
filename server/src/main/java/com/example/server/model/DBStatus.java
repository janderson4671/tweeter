package com.example.server.model;

public class DBStatus {

    public DBStatus(String userAlias, String timeStamp, String message) {
        this.userAlias = userAlias;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    String userAlias;
    String timeStamp;
    String message;

}
