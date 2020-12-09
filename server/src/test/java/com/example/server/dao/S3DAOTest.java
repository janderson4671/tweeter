package com.example.server.dao;

import com.example.shared.util.ByteArrayUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class S3DAOTest {

    byte [] profile;
    String username;
    S3DAO dao;

    @BeforeEach
    public void setup() {

        try {
            profile = ByteArrayUtils.bytesFromUrl("https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            username = "@person198";
            dao = new S3DAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileUpload() {
        String responseURL = dao.uploadProfileImage(profile, username);

        Assertions.assertNotNull(responseURL);
        Assertions.assertEquals(responseURL, "https://profile-images-tweeter.s3.us-east-2.amazonaws.com/%40person198");
    }

}
