package com.example.server.dao;

import com.example.server.model.DBStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FeedDAOTest {

    String userAlias;
    int limit;

    FeedDAO dao;

    @BeforeEach
    public void setup() {
        limit = 10;
        userAlias = "@person198";
        dao = new FeedDAO();
    }

    @Test
    public void getFeedAndBatchPostTest() {
        List<DBStatus> statuses;

        try {
            statuses = dao.getFeed(userAlias, null, limit);
            Assertions.assertNotNull(statuses);

            dao.batchPost(statuses);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

}
