package com.example.server.dao;

import com.example.shared.domain.AuthToken;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthTokenDAOTest {

    static AuthToken authToken;

    static AuthTokenDAO dao = new AuthTokenDAO();

    @BeforeAll
    static void initAuthToken() {
        authToken = new AuthToken();
        dao.createSession(authToken);
    }

    @Test
    public void createSessionTest() {
        try {
            dao.createSession(authToken);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void updateSessionTest() {
        try {
            dao.updateSession(authToken);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void validateUserTest() {
        try {
            dao.validateUser(authToken);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

    @Test
    public void destroySessionTest() {
        try {
            dao.destroySession(authToken);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Assertions.fail();
        }
    }

}
