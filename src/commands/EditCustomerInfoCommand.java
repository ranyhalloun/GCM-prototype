package commands;

import application.customer.Customer;

public class EditCustomerInfoCommand {
	//input
	private Customer customer;
	private boolean newUsername;
	
	//output
	private int success;
	
	
	public EditCustomerInfoCommand(Customer customer, boolean newUsername) {
		this.customer = customer;
		this.newUsername = newUsername;
		this.success = -1;
	}
	
	//Getters
	public Customer getCustomer() {
		return this.customer;
	}
	
	public boolean getNewUsername() {
		return this.newUsername;
	}
	
	public int getSuccess() {
		return this.success;
	}
	
	//Setters
	public void setSuccess(int success) {
		this.success = success;
	}
}