package com.example.server.model;

public class DBStatus {

    public DBStatus(String userAlias, String timeStamp, String message) {
        this.userAlias = userAlias;
        this.timeStamp = timeStamp;
        this.message = message;
        this.author = "";
    }

    public DBStatus(String userAlias, String timeStamp, String message, String author) {
        this.userAlias = userAlias;
        this.timeStamp = timeStamp;
        this.message = message;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    String userAlias;
    String timeStamp;
    String message;
    String author;

}
