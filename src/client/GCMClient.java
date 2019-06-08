package client;

import ocsf.client.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

import Users.UserType;
import application.Main;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
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
        boolean success = command.getCommand(SigninCommand.class).getSuccess();
        if (success) {
            userType = command.getCommand(SigninCommand.class).getRole();
            Main.getInstance().updateUserType(userType);
            System.out.println("You have successfully signed in!");
            System.out.println("Role: " + userType);
            switch(userType) {
                case Anonymous:
                case Customer:
                case Worker:
                    Main.getInstance().goToSearchMap();
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
        } else {
            System.out.println("Sign in failed.");
        }
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

    public void handleSearchMap(String attraction, String cityName, String description) throws IOException
    {
        // Implement here
        System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
        Command command = new Command(new SearchMapCommand(attraction, cityName, description), CommandType.SearchMapCommand);
        sendToServer(command);
    }
    
    public void handleInsertNewMap(int id, String cityName, String description, String imagePath) throws IOException
    {
        System.out.println("handleInsertNewMap");
        Command command = new Command(new InsertMapCommand(id, cityName, description, imagePath), CommandType.InsertMapCommand);;
        sendToServer(command);
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
