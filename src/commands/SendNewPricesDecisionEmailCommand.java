package commands;

import java.io.Serializable;

public class SendNewPricesDecisionEmailCommand implements Serializable {

	//input
	private String emailAddress;
	private String decision;
	
	public SendNewPricesDecisionEmailCommand(int decision, String emailAddress) {
		this.emailAddress = emailAddress;
		this.decision = decision == 1 ? "accept" : "decline";
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
}
