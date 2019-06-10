package application.customer;
import java.io.IOException;

import application.Main;
import application.boolObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField username;
	    @FXML
	    void back(ActionEvent event) throws IOException {
	    	System.out.println("Going back to customer services");
	    	Main.getInstance().goToCostumerServices();
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
	    void History(ActionEvent event) {

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
            }
            else
                getCustomer(customer);
	    	
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
	    }
	    
	    @FXML
	    void initialize() {
	    	fieldsStatus(false);
	    	saveBtn.setVisible(false);
	    	cancelBtn.setVisible(false);
	    }
	}

