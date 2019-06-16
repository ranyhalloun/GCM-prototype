package commands;

import java.io.Serializable;

public class SendNewPricesRequestEmailCommand implements Serializable{

	//input
	private String emailAddress;
	
	public SendNewPricesRequestEmailCommand(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
}
