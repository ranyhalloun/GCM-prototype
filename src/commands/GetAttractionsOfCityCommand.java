package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.Attraction;

public class GetAttractionsOfCityCommand implements Serializable{
	
	//input
	private String cityName;
	private int tourID;
	
	//output
	private boolean success;
	private String error;
	private ArrayList<Attraction> attractions;
	
	public GetAttractionsOfCityCommand(String cityName, int tourID)
	{
		this.cityName = cityName;
		this.success = false;
		this.tourID = tourID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public ArrayList<Attraction> getAttractions() {
		return attractions;
	}

	public void setAttractions(ArrayList<Attraction> attractions) {
		this.attractions = attractions;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
}
