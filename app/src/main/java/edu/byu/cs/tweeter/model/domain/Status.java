package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Status implements Serializable {

    private User user;
    private List<String> mentionAliases;

    private String message;
    private Date timeStamp;

    //Constructor
    public Status(User user, String message, Date timeStamp, List<String> mentionAliases) {
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
        this.mentionAliases = mentionAliases;
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

    public List<String> getMentionAliases() {
        return mentionAliases;
    }

    public void addMention(User mention) {
        mentionAliases.add(mention.getAlias());
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
