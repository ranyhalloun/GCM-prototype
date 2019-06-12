package commands;
import java.io.Serializable;
import java.util.ArrayList;

import Entities.Attraction;

public class AddAttractionToTourCommand implements Serializable{

	//input
	private String attractionID;
	private int tourID;
	private int time;
	
	//output
	private boolean success;
	private String error;
	
	public AddAttractionToTourCommand(String attractionID, int tourID, int time)
	{
		this.attractionID = attractionID;
		this.success = false;
		this.tourID = tourID;
		this.time = time;
	}

	public String getAttractionId() {
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
}