package commands;

import java.io.Serializable;

public class SendNewEditedMapsEmailCommand implements Serializable{

	//input
	private String emailAddress;
	
	public SendNewEditedMapsEmailCommand(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
