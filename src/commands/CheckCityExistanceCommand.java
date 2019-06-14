package commands;

import java.io.Serializable;

public class CheckCityExistanceCommand implements Serializable{

	//input
	private String cityName;
	
	//output
	private boolean success;
	
	public CheckCityExistanceCommand(String cityName) {
		this.cityName = cityName;
		this.success = false;
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
	
	
}
