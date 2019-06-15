package application.customer;
import java.io.IOException;

import application.Main;
import application.boolObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Users.UserType;

public class customerProfileController {

	
	private Customer customer;
	
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button historyBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private Button editBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField email;
    
    @FXML
    private Button saveBtn;
    
    @FXML
    private Button cancelBtn;

    @FXML
    private Label errorText;
    
    @FXML
    private Label successText;

    @FXML
    private TextField username;
	    @FXML
	    void back(ActionEvent event) throws IOException {
	    	switch(Main.getInstance().getUserType()) {
	    		case Worker:
	    			Main.getInstance().goToWorkerServices();
	    			break;
	    		case Customer:
	    			Main.getInstance().goToCustomerServices();
	    			break;
	    		case GCMWorker:
	    			Main.getInstance().goToGCMWorkerServices();
	    			break;
	            case GCMManager:
	                Main.getInstance().goToGCMManagerServices();
	                break;
	            case CompanyManager:
	                Main.getInstance().goToCompanyManagerServices();
	                break;
	            	
	    	}
	    }

	    @FXML
	    void edit(ActionEvent event) {
	    	fieldsStatus(true);
	    	saveBtn.setVisible(true);
	    	cancelBtn.setVisible(true);
	    	historyBtn.setVisible(false);
	    	editBtn.setVisible(false);
	    	backBtn.setVisible(false);
	    }

	    @FXML
	    void History(ActionEvent event) throws IOException {
	    	Main.getInstance().goToPurchasesHistory(customer.getUsername());
	    }
	    

	    @FXML
	    void save(ActionEvent event) throws IOException {
	        boolObject exists = new boolObject();
	    	Customer newCustomer = new Customer(username.getText(),password.getText(),firstName.getText(),
	    									lastName.getText(), email.getText(), phone.getText());
	    	String oldUsername = customer.getUsername();
            Main.getInstance().editCustomerInfo(newCustomer, oldUsername, exists);

            if(!exists.getValue()) {
                getCustomer(newCustomer);
                viewState();
                successText.setText("Change saved");
                errorText.setText("");
            }
            else
                {
            		getCustomer(customer);
            		errorText.setText("Username exists!");
            		successText.setText("");
                }
	    }

	    @FXML
	    void cancel(ActionEvent event) {
	    	getCustomer(customer);
	    	viewState();
	    }
	    
	    public void getCustomer(Customer customer) {
	    	this.customer = customer;
	    	firstName.setText(customer.getFirstName());
	    	lastName.setText(customer.getLastName());
	    	username.setText(customer.getUsername());
	    	password.setText(customer.getPassword());
	    	email.setText(customer.getEmail());
	    	phone.setText(customer.getPhone());
	    }
	    
	    public void fieldsStatus(boolean status) {
	    	firstName.setEditable(status);
	    	lastName.setEditable(status);
	    	username.setEditable(status);
	    	password.setEditable(status);
	    	email.setEditable(status);
	    	phone.setEditable(status);
	    	
	    }
	    
	    public void viewState() {
	    	fieldsStatus(false);
	    	saveBtn.setVisible(false);
	    	cancelBtn.setVisible(false);
	    	historyBtn.setVisible(true);
	    	editBtn.setVisible(true);
	    	backBtn.setVisible(true);
	    	switch(Main.getInstance().getUserType()) {
    		case Customer:
    			historyBtn.setVisible(true);
    			break;
    		default:
    			historyBtn.setVisible(false);
    			break;
	    	}
	    }
	    
	    @FXML
	    void initialize() {
	    	fieldsStatus(false);
	    	saveBtn.setVisible(false);
	    	cancelBtn.setVisible(false);
	    	switch(Main.getInstance().getUserType()) {
	    		case Customer:
	    			historyBtn.setVisible(true);
	    			break;
	    		default:
	    			historyBtn.setVisible(false);
	    			break;
	    	}
	    }
	}

