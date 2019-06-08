package commands;

import java.io.Serializable;

enum Role 
{ 
    C, W; 
}

public class SigninCommand implements Serializable {
    
    // Input
    private String username;
    private String password;
    
    // Output
    private boolean success;
    private String role;								//change it to enum
    
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
    
    public void setRole(String role) {
    	this.role = role;
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
    
    public String getRole() {
    	return this.role;
    }
}
