package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    //TODO:: ADD image array here?

    //Constructor
    public RegisterRequest(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
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

}
