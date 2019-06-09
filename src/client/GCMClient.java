package client;

import ocsf.client.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

import Users.UserType;
import application.Main;
import application.customer.Customer;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
import commands.EditCustomerInfoCommand;
import commands.GetCustomerInfoCommand;
import commands.InsertMapCommand;
import commands.RegisterCommand;
import commands.SearchMapCommand;
import commands.SigninCommand;
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
            System.out.println("Username exists. Please choose another username.");
            break;
        case 1:
            System.out.println("Registration completed!");
            Main.getInstance().goToLogin();
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
        int success = command.getCommand(SigninCommand.class).getSuccess();
        switch (success) {
        case -1:
            System.out.println("There is a problem in the database connection");
            break;
        case 1:
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
                    // GCMManager Windows
                    break;
                case CompanyManager:
                    // CompanyManager Windows
                    break;
            }
            break;
        case 0:
            System.out.println("Sign in failed.");
            break;
        }
    }

    public void handleSearchMap(String attraction, String cityName, String description) throws IOException
    {
        // Implement here
    	commandRequest = true;
        System.out.println("handleSearchMap");
    	System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
        Command command = new Command(new SearchMapCommand(attraction, cityName, description), CommandType.SearchMapCommand);
        sendToServer(command);
        waitForServerResponse();
        handleSearchMapFromServer(attraction, cityName, description);
    }
    
    public void handleSearchMapFromServer(String attraction, String cityName, String description) throws IOException {
    	System.out.println("handleSearchMapFromServer");
        int success = command.getCommand(SearchMapCommand.class).getSuccess();
        switch (success) {
        case -1:
            System.out.println("There is a problem in the database connection");
            break;
        case 0:
            System.out.println("Map doesn't exist. Please choose another input.");
            break;
        case 1:
            System.out.println("Results are in the table!");
            break;
        }
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
        int success = command.getCommand(GetCustomerInfoCommand.class).getSuccess();
        customer.setUsername(command.getCommand(GetCustomerInfoCommand.class).getUsername());
        customer.setPassword(command.getCommand(GetCustomerInfoCommand.class).getPassword());
        customer.setFirstName(command.getCommand(GetCustomerInfoCommand.class).getFirstName());
        customer.setLastName(command.getCommand(GetCustomerInfoCommand.class).getLastName());
        customer.setEmail(command.getCommand(GetCustomerInfoCommand.class).getEmail());
        customer.setPhone(command.getCommand(GetCustomerInfoCommand.class).getPhone());
        switch (success) {
        case -1:
            System.out.println("There is a problem in the database connection");
            break;
        case 1:
            System.out.println("Customer info got successfully!");
            break;
           }
    }
    
    public void handleEditingCustomerInfo(Customer customer, boolean newUsername) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleEditingCusotmerInfo");
        Command command = new Command(new EditCustomerInfoCommand(customer, newUsername), CommandType.EditCustomerInfoCommand);
        sendToServer(command);
        System.out.println("HEllloooooo");
        waitForServerResponse();
        handleGetCustomerInfoFromServer(customer);
        System.out.println(customer.getPassword());
    }
    
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
