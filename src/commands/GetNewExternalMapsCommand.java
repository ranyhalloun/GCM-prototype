package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.Map;

public class GetNewExternalMapsCommand implements Serializable{

	//output
	private boolean success;
	private String error;
	private ArrayList<Map> maps;
	
	public GetNewExternalMapsCommand() {
		setSuccess(false);
		setError("");
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

	public ArrayList<Map> getMaps() {
		return maps;
	}

	public void setMaps(ArrayList<Map> maps) {
		this.maps = maps;
	}
}
