package com.example.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private String firstName;
    private String lastName;
    private String alias;
    private String imageUrl;
    private List<Status> posts;
    private int numFollowers;
    private int numFollowing;
    private byte[] imageBytes;

    //Constructors
    public User() {}
    public User(String firstName, String lastName, String imageURL, int numFollowers, int numFollowing) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, numFollowers, numFollowing);
    }

    public User(String firstName, String lastName, String alias, String imageURL, int numFollowers, int numFollowing) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;

        posts = new ArrayList<>();
    }

    //Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {

        if (alias == null) {
            alias = String.format("@%s%s", firstName, lastName);
        }

        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Status> getPosts() {
        return posts;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    //Setters
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public void setPosts(List<Status> posts) {
        this.posts = posts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //Overriden Functions
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.getAlias().equals(user.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }

    //Utility
    public void addPost(Status status) {
        posts.add(status);
    }
}
