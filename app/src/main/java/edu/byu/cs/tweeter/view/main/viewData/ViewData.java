package edu.byu.cs.tweeter.view.main.viewData;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import com.example.shared.domain.AuthToken;
import com.example.shared.domain.User;

public class ViewData {

    private static ViewData data;

    private static User loggedInUser;
    private static AuthToken authToken;

    private static List<User> allMentionedUsers;

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

    public List<User> getAllMentionedUsers() { return allMentionedUsers; }

    //Setters
    public static void setLoggedInUser(User user, AuthToken authTokenIn) {
        loggedInUser = user;
        authToken = authTokenIn;
    }

    //Private Constructor
    private ViewData() {
        loggedInUser = null;
        authToken = null;
        allMentionedUsers = new ArrayList<>();
    }

    public void addMentionedUsers(List<User> users) {

        if (users == null) {
            return;
        }

        for (User currUser : users) {
            if (!allMentionedUsers.contains(currUser)) {
                allMentionedUsers.add(currUser);
            }
        }
    }

}
