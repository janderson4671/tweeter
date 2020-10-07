package edu.byu.cs.tweeter.view.main;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class ViewData {

    private static ViewData data;

    private static User loggedInUser;
    private static AuthToken authToken;

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

    //Setters
    public static void setLoggedInUser(User user, AuthToken authTokenIn) {
        loggedInUser = user;
        authToken = authTokenIn;
    }

    //Private Constructor
    private ViewData() {
        loggedInUser = null;
        authToken = null;
    }

}
