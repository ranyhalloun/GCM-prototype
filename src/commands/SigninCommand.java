package commands;

import java.io.Serializable;

import Users.UserType;

public class SigninCommand implements Serializable {
    
    // Input
    private String username;
    private String password;
    
    // Output
    private int success;
    private UserType role;
    
    
    // Constructor
    public SigninCommand(String username, String password) {
        this.username = username;
        this.password = password;
        this.success = -1;
    }
    
    // Setters
    public void setSuccess(int success) {
        this.success = success;
    }
    
    public void setRole(UserType role) {
        this.role = role;
    }
    
    // Getters
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public int getSuccess() {
        return this.success;
    }
    
    public UserType getRole() {
        return this.role;
    }
}
