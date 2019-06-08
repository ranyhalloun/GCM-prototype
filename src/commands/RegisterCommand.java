package commands;

import java.io.Serializable;

public class RegisterCommand implements Serializable {
    
    // Input
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;
    
    // Output
    private boolean success;
    private String error = "";
    
    // Constructor
    public RegisterCommand(String firstname, String lastname, String username, String password, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.success = false;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setError(String error) {
    	this.error = error;
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
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getError() {
    	return this.error;
    }
    
}
