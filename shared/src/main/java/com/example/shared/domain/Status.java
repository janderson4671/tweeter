package com.example.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Status implements Serializable {

    public void setUser(User user) {
        this.user = user;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Status{" +
                "user=" + user +
                ", mentions=" + mentions +
                ", message='" + message + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }

    private User user;
    private List<User> mentions;
    private String message;
    private String timeStamp;

    public Status() {}

    //Constructor
    public Status(User user, String message, String timeStamp, List<User> mentions) {
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
        this.mentions = mentions;
    }

    //Getters
    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public List<User> getMentions() {
        return mentions;
    }

    public void addMention(User mention) {
        mentions.add(mention);
    }

    public void setUserImage(byte [] imageBytes) {
        //user.setImageBytes(imageBytes);
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
