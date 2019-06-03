package commands;

import java.io.Serializable;

public class RegisterCommand implements Serializable {
    
    // Input
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    
    // Output
    private boolean success;
    
    // Constructor
    public RegisterCommand(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.success = false;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    // Getters
    public String getFirstname() {
        return this.firstname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
}
