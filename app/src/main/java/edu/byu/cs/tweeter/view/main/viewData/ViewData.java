package edu.byu.cs.tweeter.view.main.viewData;

import android.graphics.Bitmap;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class ViewData {

    private static ViewData data;

    private static User loggedInUser;
    private static AuthToken authToken;

    private static List<User> allMentionedUsers;

    private static Bitmap profile;

    public static ViewData getData() {
        if (data == null) {
            data = new ViewData();
        }

        return data;
    }

    //Getters
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public Bitmap getProfile() { return profile; }

    public List<User> getAllMentionedUsers() { return allMentionedUsers; }

    //Setters
    public static void setLoggedInUser(User user, AuthToken authTokenIn, Bitmap profileIn) {
        loggedInUser = user;
        authToken = authTokenIn;
        profile = profileIn;
    }

    //Private Constructor
    private ViewData() {
        loggedInUser = null;
        authToken = null;
        profile = null;
    }

    public void addMentionedUsers(List<User> users) {
        for (User currUser : users) {
            if (!allMentionedUsers.contains(currUser)) {
                allMentionedUsers.add(currUser);
            }
        }
    }

}
