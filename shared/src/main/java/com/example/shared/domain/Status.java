package com.example.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Status implements Serializable {

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setMentionAliases(List<String> mentionAliases) {
        this.mentionAliases = mentionAliases;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userAlias;
    private String userImageURL;
    private String userName;
    private List<String> mentionAliases;
    private String message;
    private String timeStamp;

    public Status() {}

    //Constructor
    public Status(String userAlias, String message, String timeStamp, List<String> mentionAliases, String userName, String userImageURL) {
        this.userAlias = userAlias;
        this.message = message;
        this.timeStamp = timeStamp;
        this.mentionAliases = mentionAliases;

        if (userName == null) {
            userName = "Jason Anderson";
        }
        if (userImageURL == null) {
            userImageURL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        }
    }

    //Getters
    public String getUserAlias() {
        return userAlias;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public List<String> getMentionAliases() {
        return mentionAliases;
    }

    public void addMention(User mention) {
        mentionAliases.add(mention.getAlias());
    }

    public void setUserImage(byte [] imageBytes) {
        //user.setImageBytes(imageBytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return userAlias.equals(status.userAlias) &&
                message.equals(status.message); /*&&
                timeStamp.equals(status.timeStamp);*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAlias, message, timeStamp);
    }
}
