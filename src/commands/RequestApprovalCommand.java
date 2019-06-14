package commands;

import java.io.Serializable;

public class RequestApprovalCommand implements Serializable {

    // Input
    private String cityName;

    // Output
    private int success;

    // Constructor
    public RequestApprovalCommand(String cityName) {
        this.cityName = cityName;
    }

    // Setters
    public void setSuccess(int success) {
        this.success = success;
    }

    // Getters
    public String getCityName() {
        return this.cityName;
    }

    public int getSuccess() {
        return this.success;
    }

}