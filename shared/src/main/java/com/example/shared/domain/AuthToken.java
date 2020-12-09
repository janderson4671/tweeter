package com.example.shared.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {

    private static int SESSION_TIMEOUT_MINS = 30;

    private String token;
    private String expirationDate;

    public AuthToken() {
        token = UUID.randomUUID().toString();
        expirationDate = LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINS).toString();
    }

    public AuthToken(String token, String expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
