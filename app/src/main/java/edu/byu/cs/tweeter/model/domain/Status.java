package edu.byu.cs.tweeter.model.domain;

import java.util.Date;

public class Status {

    private User user;

    private String message;
    private Date timeStamp;

    //Constructor
    public Status(User user, String message, Date timeStamp) {
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    //Getters
    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

}
