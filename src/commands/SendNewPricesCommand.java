package commands;

import java.io.Serializable;

public class SendNewPricesCommand implements Serializable{

	//input
	private String newOnePrice;
	private String newSubsPrice;
	
	public SendNewPricesCommand(String newOnePrice, String newSubsPrice) {
		this.newOnePrice = newOnePrice;
		this.newSubsPrice = newSubsPrice;
	}

	public String getNewOnePrice() {
		return newOnePrice;
	}

	public void setNewOnePrice(String newOnePrice) {
		this.newOnePrice = newOnePrice;
	}

	public String getNewSubsPrice() {
		return newSubsPrice;
	}

	public void setNewSubsPrice(String newSubsPrice) {
		this.newSubsPrice = newSubsPrice;
	}
}
