package commands;

import java.io.Serializable;

public class CheckCustomerCommand implements Serializable{

	//input
	private String username;
	
	//output
	private boolean exists;
	
	public CheckCustomerCommand(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
}
