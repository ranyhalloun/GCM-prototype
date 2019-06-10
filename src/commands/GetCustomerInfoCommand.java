package commands;
import java.io.Serializable;

public class GetCustomerInfoCommand implements Serializable{

    // Output
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;
	private boolean success;
    private String error = "";
    
    // Constructor
        public GetCustomerInfoCommand(){
        	this.success = false;
        }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Getters
  	public String getFirstName() {
  		return this.firstName;
  	}
  	public String getLastName() {
  		return this.lastName;
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