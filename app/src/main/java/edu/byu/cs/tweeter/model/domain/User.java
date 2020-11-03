package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String imageUrl;

    private byte [] imageBytes;

    private List<User> followers;
    private List<User> following;

    //TODO: Impliment These!!
    private int numFollowers;
    private int numFollowing;

    private List<Status> posts;
    private List<Status> feed;
    private List<Status> story;

    //Constructors

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL);
    }
    public User(String firstName, String lastName, String alias, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;

        followers = new ArrayList<>();
        following = new ArrayList<>();
        posts = new ArrayList<>();
        feed = new ArrayList<>();
        story = new ArrayList<>();
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
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public byte [] getImageBytes() {
        return imageBytes;
    }

    public List<Status> getPosts() {
        return posts;
    }

    public void addPost(Status status) {
        posts.add(status);
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
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

    public void addFollowers(List<User> followers) {
        this.followers.addAll(followers);
    }

    public void addFollowing(List<User> following) {
        this.following.addAll(following);
    }

    public void addFeedStatuses(List<Status> newStatuses) {
        feed.addAll(newStatuses);
    }

    public void addStoryStatuses(List<Status> newStatuses) {
        story.addAll(newStatuses);
    }

    public List<Status> getFeed() {
        return feed;
    }

    public List<Status> getStory() {
        return story;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
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
