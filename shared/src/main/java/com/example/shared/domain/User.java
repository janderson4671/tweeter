package com.example.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public void setPosts(List<Status> posts) {
        this.posts = posts;
    }


    private String firstName;
    private String lastName;
    private String alias;
    private String imageUrl;

    //TODO: Impliment These!!
    private int numFollowers;
    private int numFollowing;

    private List<Status> posts;

    //Constructors

    public User() {}

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, 0, 0);
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

    public int getFollowerCount() {
        return 0;
    }

    public int getFolloweeCount() {
        return 0;
    }

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

    public void addPost(Status status) {
        posts.add(status);
    }

    //TODO: Get rid of these later
    public void addFollower(User user) {
        //followers.add(new User("dummy", "follower", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
    }

    public void removeFollower(User user) {
        //followers.remove(followers.size() - 1);
    }

    public void addFollowee(User user) {
        //followees.add(new User("dummy", "follower", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
    }

    public void removeFollowee(User user) {
        //followees.remove(followees.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        System.out.println(this.getAlias());
        System.out.println(user.getAlias());
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
}
