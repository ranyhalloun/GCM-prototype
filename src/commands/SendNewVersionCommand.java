package commands;

import java.io.Serializable;

public class SendNewVersionCommand implements Serializable{
	
	//input
	private String emailAddress;
	private String cityName;
	
	public SendNewVersionCommand(String emailAddress, String cityName) {
		this.emailAddress = emailAddress;
		this.cityName = cityName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
