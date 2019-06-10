package commands;

import java.io.Serializable;

public class RequestApprovalCommand implements Serializable {

	// Input
    private String cityName;
    
    // Output
    private boolean success;
    
    // Constructor
    public RequestApprovalCommand(String cityName) {
        this.cityName = cityName;
        this.success = false;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    // Getters
    public String getCityName() {
        return this.cityName;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
}
