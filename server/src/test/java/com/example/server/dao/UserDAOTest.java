package com.example.server.dao;

import com.example.shared.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

    User user;
    String password;
    UserDAO dao;

    @BeforeEach
    public void setup() {
        user = new User("Person 198", "Test", "@person198", "https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40person198", 0, 0);
        password = "9272278267573875888704606116539863834568850279235510529322262607639144781307745483087347330528189813124635315521048674837107446877650066414960134245366918";
        dao = new UserDAO();
    }

    @Test
    public void addUserTest() {
        try {
            dao.addUser(user, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void updateFollowingTest() {

        try {
            dao.updateFollowing(user.getAlias(), true);
            dao.updateFollowing(user.getAlias(), false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void updateFollowerTest() {

        try {
            dao.updateFollower(user.getAlias(), true);
            dao.updateFollower(user.getAlias(), false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void getUserTest() {

        User resultUser = new User();

        try {
            resultUser = dao.getUser(user.getAlias());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(resultUser, user);

    }

    @Test
    public void getUserPasswordTest() {
        String expectedPassword = "";

        try {
            expectedPassword = dao.getUserPassword(user.getAlias());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(expectedPassword);
        Assertions.assertEquals(expectedPassword, password);
    }

    @Test
    public void correctPasswordTest() {
        boolean success;

        try {
            success = dao.correctPassword(user.getAlias(), password);
            Assertions.assertTrue(success);

            success = dao.correctPassword(user.getAlias(), "password");
            Assertions.assertFalse(success);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
