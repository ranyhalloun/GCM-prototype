package commands;
import java.io.Serializable;
import java.util.ArrayList;

import Entities.Attraction;

public class AddAttractionToTourCommand implements Serializable{

	//input
	private Attraction attraction;
	private int tourID;
	private int time;
	
	//output
	private boolean success;
	private String error;
	
	public AddAttractionToTourCommand(Attraction attraction, int tourID, int time)
	{
		this.attraction = attraction;
		this.success = false;
		this.tourID = tourID;
		this.time = time;
	}

	public Attraction getAttraction() {
		return attraction;
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