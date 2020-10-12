package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private byte [] profile;

    //Constructor
    public RegisterRequest(String firstName, String lastName, String username, String password, byte[] profile) {
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

}
