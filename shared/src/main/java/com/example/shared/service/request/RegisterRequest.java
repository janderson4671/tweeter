package com.example.shared.service.request;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private byte [] profile;

    //Constructors
    public RegisterRequest() {}

    public RegisterRequest(String firstName, String lastName, String username, String password, byte [] profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    //Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public byte [] getProfile() { return profile; }

    //Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }
}
