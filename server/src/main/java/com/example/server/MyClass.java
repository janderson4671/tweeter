package com.example.server;

import com.example.server.dao.FollowDAO;
import com.example.server.dao.UserDAO;
import com.example.shared.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    private static final int NUM_USERS = 10000;
    private static final String FOLLOW_TARGET = "@merp";

    public static void main(String[] args) {
        List<String> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= NUM_USERS; i++) {
            String firstName = "Person " + i;
            String lastName = "Test";
            String alias = "@person" + i;
            String imageURL;
            if (i % 2 == 0) {
                imageURL = "https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40blah";
            } else {
                imageURL = "https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40nate";
            }

            User user = new User(firstName, lastName, alias, imageURL, 0, 1);

            users.add(user);

            followers.add(alias);
        }

        if (users.size() > 0) {
            for (User currUser : users) {
                UserDAO.addUser(currUser, "9272278267573875888704606116539863834568850279235510529322262607639144781307745483087347330528189813124635315521048674837107446877650066414960134245366918"); //Password
            }
        }
        if (followers.size() > 0) {
            for (String follower : followers) {
                FollowDAO.follow(follower, FOLLOW_TARGET);
            }
        }

    }

}