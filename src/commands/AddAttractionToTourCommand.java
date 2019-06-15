package commands;
import java.io.Serializable;
import java.util.ArrayList;

import Entities.Attraction;

public class AddAttractionToTourCommand implements Serializable {

	//input
	private int attractionID;
	private int tourID;
	private int time;
	private String cityName;
	
	//output
	private boolean success;
	private String error;
	
	public AddAttractionToTourCommand(int attractionID, int tourID, int time, String cityName)
	{
		this.attractionID = attractionID;
		this.success = false;
		this.tourID = tourID;
		this.time = time;
		this.cityName = cityName;
	}

	public int getAttractionId() {
		return attractionID;
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

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}