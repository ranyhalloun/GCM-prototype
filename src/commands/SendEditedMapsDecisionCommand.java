package commands;

import java.io.Serializable;

public class SendEditedMapsDecisionCommand implements Serializable{

	//input
	private String emailAddress;
	private String decision;
	private String cityName;
	
	public SendEditedMapsDecisionCommand(int decision, String emailAddress, String cityName) {
		this.emailAddress = emailAddress;
		this.decision = decision == 1 ? "accept" : "decline";
		this.cityName = cityName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
