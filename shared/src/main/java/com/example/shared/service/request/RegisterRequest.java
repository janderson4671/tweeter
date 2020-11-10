package com.example.shared.service.request;

public class RegisterRequest {

    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public byte [] profile;

    //Constructor
    public RegisterRequest(String firstName, String lastName, String username, String password, byte [] profile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public RegisterRequest() {
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

}
