package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.DownloadDetails;


public class GetDownloadsCommand implements Serializable{

	//input
	private String customerUsername;
	
	//output
	private ArrayList<DownloadDetails> downloads;
	
	public GetDownloadsCommand(String customerUsername) {
		this.setCustomerUsername(customerUsername);
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public ArrayList<DownloadDetails> getDownloads() {
		return downloads;
	}

	public void setDownloads(ArrayList<DownloadDetails> downloads) {
		this.downloads = downloads;
	}
}
