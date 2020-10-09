package edu.byu.cs.tweeter.model.domain;

import java.util.Date;
import java.util.Objects;

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

    public void setUserImage(byte [] imageBytes) {
        user.setImageBytes(imageBytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return user.equals(status.user) &&
                message.equals(status.message); /*&&
                timeStamp.equals(status.timeStamp);*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, timeStamp);
    }
}
