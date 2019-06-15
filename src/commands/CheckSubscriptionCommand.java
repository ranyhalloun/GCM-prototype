package commands;

import java.io.Serializable;

public class CheckSubscriptionCommand implements Serializable{

	//input
	private String cityName;
	
	//output
	private boolean exists;

	public CheckSubscriptionCommand(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean getExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
	
}
