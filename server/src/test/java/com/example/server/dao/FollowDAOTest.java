package com.example.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FollowDAOTest {

    String userAlias;
    String userToFollow;

    int limit;

    FollowDAO dao;

    @BeforeEach
    public void setup() {
        userAlias = "@person198";
        userToFollow = "@person200";
        limit = 10;

        dao = new FollowDAO();
    }

    @Test
    public void followAnotherUserTest() {

        boolean success;

        try {
            success = dao.follow(userAlias, userToFollow);
            Assertions.assertTrue(success);

            boolean doesFollow = dao.doesFollow(userAlias, userToFollow);
            Assertions.assertTrue(doesFollow);

            success = dao.unFollow(userAlias, userToFollow);
            Assertions.assertTrue(success);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.fail();
        }
    }

    @Test
    public void getFollowingTest() {
        List<String> following;

        try {
            dao.follow(userAlias, userToFollow);

            following = dao.getFollowing(userAlias, null, limit);
            Assertions.assertNotNull(following);
            Assertions.assertTrue(following.size() > 0);

            dao.unFollow(userAlias, userToFollow);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void getUsersThatFollowTest() {
        List<String> followers;

        try {
            dao.follow(userToFollow, userAlias);

            followers = dao.getUsersThatFollow(userAlias, null, limit);
            Assertions.assertNotNull(followers);
            Assertions.assertTrue(followers.size() > 0);

            dao.unFollow(userToFollow, userAlias);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    //Using the user with 10000 followers here
    @Test
    public void getAllUsersThatFollow() {
        List<String> followers;

        try {
            followers = dao.getAllUsersThatFollow("@merp");
            Assertions.assertNotNull(followers);
            Assertions.assertTrue(followers.size() > 5000);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

}
