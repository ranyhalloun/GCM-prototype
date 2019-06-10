package commands;

import application.customer.Customer;
import java.io.Serializable;

public class EditCustomerInfoCommand implements Serializable {
	//input
	private Customer customer;
	private String oldUsername;
	
	//output
	private boolean success;
	
	
	public EditCustomerInfoCommand(Customer customer, String oldUsername) {
		this.customer = customer;
		this.oldUsername = oldUsername;
        this.success = false;
	}
	
	//Getters
	public Customer getCustomer() {
		return this.customer;
	}
	
	public String getNewUsername() {
		return this.oldUsername;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	//Setters
	public void setSuccess(boolean success) {
		this.success = success;
	}
}