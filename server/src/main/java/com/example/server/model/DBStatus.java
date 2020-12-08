package com.example.server.model;

import java.io.Serializable;

public class DBStatus implements Serializable {

    public DBStatus() {}

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

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    String userAlias;
    String timeStamp;
    String message;
    String author;

}
