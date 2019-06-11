package client;

import ocsf.client.*;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import Users.UserType;
import application.Main;
import application.arrayOfStrings;
import application.boolObject;
import application.customer.Customer;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
import commands.EditCustomerInfoCommand;
import commands.GetCitiesQueueCommand;
import commands.GetCityToursCommand;
import commands.GetCustomerInfoCommand;
import commands.InsertMapCommand;
import commands.RegisterCommand;
import commands.RequestApprovalCommand;
import commands.SearchMapCommand;
import commands.SigninCommand;
import javafx.collections.ObservableList;
import application.login.registrationController;

// **This class overrides some of the methods defined in the abstract
// **superclass in order to give more functionality to the client.

public class GCMClient extends AbstractClient {
    private Command command;
    private CommandType type;
    private boolean commandRequest;
    private UserType userType;

    public GCMClient(String host, int port) {
        super(host, port);
        commandRequest = false;
    }

    public void handleRegistration(String firstname, String lastname, String username, String password, String email, String phone) throws IOException
    {
        commandRequest = true;
        System.out.println("handleRegistration");
        command = new Command(new RegisterCommand(firstname, lastname, username, password, email, phone), CommandType.RegisterCommand);
        sendToServer(command);
        waitForServerResponse();
        handleRegisterCommandFromServer();
    }

    private void handleRegisterCommandFromServer() throws IOException
    {
        System.out.println("handleRegisterCommandFromServer");
        int success = command.getCommand(RegisterCommand.class).getSuccess();
        switch (success) {
        case -1:
            System.out.println("There is a problem in the database connection");
            break;
        case 0:
            Main.getInstance().goToRegistration("Username exists. Please choose another username");
            break;
        case 1:
            System.out.println("Registration completed!");
            Main.getInstance().goToLogin("");
            break;
        }
    }

    public void handleSignIn(String username, String password) throws IOException {
        commandRequest = true;
        System.out.println("handleSignin");
        command = new Command(new SigninCommand(username, password), CommandType.SigninCommand);
        sendToServer(command);
        waitForServerResponse();
        handleSigninCommandFromServer();
    }

    private void handleSigninCommandFromServer() throws IOException {
        System.out.println("handleSigninCommandFromServer");
        boolean success = command.getCommand(SigninCommand.class).getSuccess();
        if(success) {
        	userType = command.getCommand(SigninCommand.class).getRole();
            Main.getInstance().updateUserType(userType);
            System.out.println("You have successfully signed in!");
            System.out.println("Role: " + userType);
            switch(userType) {
                case Anonymous:
                case Worker:
                    Main.getInstance().goToSearchMap();
                    break;
                case Customer:
                	Main.getInstance().goToCostumerServices();
                	break;
                case GCMWorker:
                    Main.getInstance().goToGCMWorkerServices();
                    break;
                case GCMManager:
                    Main.getInstance().goToGCMManagerServices();
                    break;
                case CompanyManager:
                    // CompanyManager Windows
                    break;
            }
        }
        else {
            String error = command.getCommand(SigninCommand.class).getError();
        	Main.getInstance().goToLogin(error);
            System.out.println("Sign in failed.");
        }
    }

    public void handleGetCityTours(String cityName) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleSearchMap");
        Command command = new Command(new GetCityToursCommand(cityName), CommandType.GetCityToursCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetCityToursFromServer();
    }

    public void handleGetCityToursFromServer() throws IOException{
    	System.out.println("handleGetCityToursFromServer");
        boolean success = command.getCommand(GetCityToursCommand.class).getSuccess();
        
        if(success) {
            System.out.println("City tours info got successfully!");
            Main.getInstance().goToCityToursTable(command.getCommand(GetCityToursCommand.class).getTours(), command.getCommand(GetCityToursCommand.class).getCityName());
        }
        else {
            System.out.println("There is a problem in the database connection");
        }
    }
    
    public void handleSearchMap(String attraction, String cityName, String description) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleSearchMap");
    	System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
        Command command = new Command(new SearchMapCommand(attraction, cityName, description), CommandType.SearchMapCommand);
        sendToServer(command);
        waitForServerResponse();
        handleSearchMapFromServer();
    }
    
    public void handleSearchMapFromServer() throws IOException {
    	System.out.println("handleSearchMapFromServer");
        boolean success = command.getCommand(SearchMapCommand.class).getSuccess();
        if (!success) {
            System.out.println("Error occured.");
            return;
        }
        boolean searchByCity = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByCity();
        boolean searchByAttraction = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByAttraction();
        boolean searchByDescription = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByDescription();
        if(searchByCity&&!searchByAttraction&&!searchByDescription)
        	Main.getInstance().cityInfo(command.getCommand(SearchMapCommand.class).getSearchMapResult());
        
        else if(!searchByCity&&searchByAttraction&&!searchByDescription)
        	Main.getInstance().attractionInfo(command.getCommand(SearchMapCommand.class).getSearchMapResult());
        else
        	Main.getInstance().goToMapsTable(command.getCommand(SearchMapCommand.class).getSearchMapResult());
    }
    
    public void handleInsertNewMap(int id, String cityName, String description, String imagePath) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleInsertNewMap");
        Command command = new Command(new InsertMapCommand(id, cityName, description, imagePath), CommandType.InsertMapCommand);;
        sendToServer(command);
        waitForServerResponse();
        handleInsertNewMapFromServer();
    }
    
    public void handleInsertNewMapFromServer() {
    	System.out.println("handleInsertNewMapFromServer");
        int success = command.getCommand(InsertMapCommand.class).getSuccess();
        switch (success) {
        case -1:
            System.out.println("There is a problem in the database connection");
            break;
        case 0:
            System.out.println("Map exists. Please choose another map(change mapID).");
            break;
        case 1:
            System.out.println("Map insertion completed!");
            break;
        }
    }
    public void handleGetCustomerInfo(Customer customer) throws IOException
    {
    	
    	commandRequest = true;
        System.out.println("handleGetCustomerInfo");
        Command command = new Command(new GetCustomerInfoCommand(), CommandType.GetCustomerInfoCommand);;
        sendToServer(command);
        waitForServerResponse();
        handleGetCustomerInfoFromServer(customer);
        System.out.println(customer.getPassword());

    }
    
    public void handleGetCustomerInfoFromServer(Customer customer) {
    	System.out.println("handleGetCustomerInfoFromServer");
        boolean success = command.getCommand(GetCustomerInfoCommand.class).getSuccess();
        customer.setUsername(command.getCommand(GetCustomerInfoCommand.class).getUsername());
        customer.setPassword(command.getCommand(GetCustomerInfoCommand.class).getPassword());
        customer.setFirstName(command.getCommand(GetCustomerInfoCommand.class).getFirstName());
        customer.setLastName(command.getCommand(GetCustomerInfoCommand.class).getLastName());
        customer.setEmail(command.getCommand(GetCustomerInfoCommand.class).getEmail());
        customer.setPhone(command.getCommand(GetCustomerInfoCommand.class).getPhone());
        if(success)
            System.out.println("Customer info got successfully!");
        else
            System.out.println("There is a problem in the database connection");
    }
    
    public void handleGetCitiesQueue(arrayOfStrings cities) throws IOException {
        commandRequest = true;
        System.out.println("handleGetCitiesQueue");
        Command command = new Command(new GetCitiesQueueCommand(), CommandType.GetCitiesQueueCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetCitiesQueueFromServer(cities);
    }
    
    public void handleGetCitiesQueueFromServer(arrayOfStrings cities) {
        System.out.println("handleGetCitiesQueueFromServer");
        boolean success = command.getCommand(GetCitiesQueueCommand.class).getSuccess();
        cities.setArrayList(command.getCommand(GetCitiesQueueCommand.class).getCities());
        if(success)
            System.out.println("Cities info got successfully!");
        else
            System.out.println("There is a problem in the database connection");
    }
    
    public void handleEditingCustomerInfo(Customer customer, String oldUsername, boolObject exists) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleEditingCusotmerInfo");
        Command command = new Command(new EditCustomerInfoCommand(customer, oldUsername), CommandType.EditCustomerInfoCommand);
        sendToServer(command);
        waitForServerResponse();
        handleEditingCustomerInfoFromServer(customer, exists);
    }
    
    public void handleEditingCustomerInfoFromServer(Customer customer, boolObject exists) {
        System.out.println("handleEditingCustomerInfoFromServer");
        exists.setValue(!command.getCommand(EditCustomerInfoCommand.class).getSuccess());
        if (!exists.getValue())
            System.out.println("Customer info updated successfully!");
        else
            System.out.println("User name exists!");
    }

    public void handleRequestApproval(String cityName) throws IOException {
        commandRequest = true;
        System.out.println("handleRequestApproval");
        Command command = new Command(new RequestApprovalCommand(cityName), CommandType.RequestApprovalCommand);
        sendToServer(command);
        waitForServerResponse();
        handleRequestApprovalFromServer(cityName);
    }

    public void handleRequestApprovalFromServer(String cityName) {
        System.out.println("handleEditingCustomerInfoFromServer");
        boolean success = command.getCommand(RequestApprovalCommand.class).getSuccess();
        if (success)
            System.out.println("Request have sent successfully!");
        else
            System.out.println("CityName doesn't exist! OR already sent");
    }
    
    /*public void handleRemoveAttractionFromTour(StringIntPair attraction) {
    	commandRequest = true;
        System.out.println("handleRequestApproval");
        Command command = new Command(new removeAttractionFromTourCommand(attraction), CommandType.RequestApprovalCommand);
        sendToServer(command);
        waitForServerResponse();
        handleRemoveAttractionFromTourFromServer();
    }*/
    public void handleAnonymousConnection()
    {
        
    }

    @Override
    protected void handleMessageFromServer(Object msg)
    {
        System.out.println("Handling message from server");
        command = (Command) msg;
        type = command.getType();
        System.out.println("Command type: " + type.toString());
        commandRequest = false;
    }

    public void handleShowNumOfPurchases(String username) throws IOException
    {
//        sendToServer("#numOfPurchases " + username);
    }

    public void handleIncNumOfPurchases(String username) throws IOException
    {
//        sendToServer("#incNumOfPurchases " + username);
    }

    public void handleStartConnection(String ip, int port) {       // Connecting to server
        setPort(port);
        setHost(ip);
        try {
            if (!isConnected()) {
                openConnection();
                System.out.println("You have connected server on port " + port);
                command = new Command(new ConnectionCommand(), CommandType.ConnectionCommand);
                sendToServer(command);
            }
        } catch (IOException e) {
            System.out.println("Could not connect to the server.\nPlease recheck the server"
                    + " IP and the port.");
        }
    }

    private void waitForServerResponse()
    {
        while (commandRequest) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }

    public void quit()
    {
      try {
        closeConnection();
      }
      catch(IOException e) {}
      System.exit(0);
    }

    protected void connectionException(Exception exception)
    {
      System.out.println
        ("The connection to the Server (" + getHost() + ", " + getPort() + 
        ") has been disconnected");
    }
}
