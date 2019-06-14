package commands;

import java.io.Serializable;

public class InsertNewCityCommand implements Serializable{

    //input
    private String cityName;
    private String description;

    //output
    private boolean success;
    public InsertNewCityCommand(String cityName, String description) {
        this.setCityName(cityName);
        this.description = description;
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
}