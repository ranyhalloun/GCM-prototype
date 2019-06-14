package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.PurchaseDetails;

public class GetPurchasesCommand implements Serializable{

	//input
	private String customerUsername;
	
	//output
	private ArrayList<PurchaseDetails> purchases;
	
	public GetPurchasesCommand(String customerUsername) {
		this.setCustomerUsername(customerUsername);
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public ArrayList<PurchaseDetails> getPurchases() {
		return purchases;
	}

	public void setPurchases(ArrayList<PurchaseDetails> purchases) {
		this.purchases = purchases;
	}
}
