package commands;

import java.io.Serializable;

public class SigninCommand implements Serializable {
    
    // Input
    private String username;
    private String password;
    
    // Output
    private boolean success;
    
    // Constructor
    public SigninCommand(String username, String password) {
        this.username = username;
        this.password = password;
        this.success = false;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    // Getters
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
