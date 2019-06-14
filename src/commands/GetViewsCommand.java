package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.ViewDetails;


public class GetViewsCommand implements Serializable{

	//input
	private String customerUsername;
	
	//output
	private ArrayList<ViewDetails> views;
	
	public GetViewsCommand(String customerUsername) {
		this.setCustomerUsername(customerUsername);
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public ArrayList<ViewDetails> getViews() {
		return views;
	}

	public void setViews(ArrayList<ViewDetails> views) {
		this.views = views;
	}
}
