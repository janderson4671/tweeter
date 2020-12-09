package com.example.server.dao;

import com.example.server.model.DBStatus;
import com.example.shared.domain.Status;
import com.example.shared.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StoryDAOTest {

    StoryDAO dao;

    List<DBStatus> statuses;
    String userAlias;
    int limit;
    Status post;

    @BeforeEach
    public void setup() {
        dao = new StoryDAO();
        statuses = new ArrayList<>();
        userAlias = "@person198";
        limit = 10;

        User user = new User("Person 198", "test", "@person198", null, 0, 0);
        post = new Status(user, "Hello World!", "fake-date", null);
    }

    @Test
    public void getStoryTest() {

        try {
            statuses = dao.getStory(userAlias, null, limit);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }

        Assertions.assertNotNull(statuses);
    }

    @Test
    public void postStatusTest() {
        try {
            dao.addPost(userAlias, post);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

}
