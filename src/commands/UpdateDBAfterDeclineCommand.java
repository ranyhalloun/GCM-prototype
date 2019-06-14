package commands;

import java.io.Serializable;

public class UpdateDBAfterDeclineCommand implements Serializable {

	//input
	private String cityName;
	
	//output
	private boolean success;
	private String error = "";
	
	public UpdateDBAfterDeclineCommand(String cityName) {
		this.setCityName(cityName);
		this.setSuccess(false);
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
