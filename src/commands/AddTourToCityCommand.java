package commands;

import java.io.Serializable;

public class AddTourToCityCommand implements Serializable {

    //input
    private String cityName;
    private String description;
    
    //output
    private boolean success;
    private String error;
    
    public AddTourToCityCommand(String cityName, String description)
    {
        this.setCityName(cityName);
        this.setDescription(description);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
