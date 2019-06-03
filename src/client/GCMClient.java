package client;

import ocsf.client.*;

import java.io.*;

import application.Main;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
import commands.RegisterCommand;
import commands.SigninCommand;

// **This class overrides some of the methods defined in the abstract
// **superclass in order to give more functionality to the client.

public class GCMClient extends AbstractClient {
    
    public GCMClient(String host, int port) {
        super(host, port);
    }

    public void handleRegistration(String firstname, String lastname, String username, String password) throws IOException
    {
        System.out.println("handleRegistration");
        Command command = new Command(new RegisterCommand(firstname, lastname, username, password), CommandType.RegisterCommand);
        sendToServer(command);
    }
    

    public void handleSignIn(String username, String password) throws IOException {
        System.out.println("handleSignin");
        Command command = new Command(new SigninCommand(username, password), CommandType.SigninCommand);
        sendToServer(command);
    }
    
    public void handleAnonymousConnection()
    {
        
    }

    @Override
    protected void handleMessageFromServer(Object msg)
    {
//        if (msg.toString().startsWith("#numOfPurchases ")) { // Handling #numOfPurchases message from server
//            String num = msg.toString().substring(16);
//            if (Integer.parseInt(num) == -1) num = "NULL";
//            uiController.showNumberToUser(num);
//        } else {
//            System.out.println(msg.toString());              // Other messages are printed to console
//        }
        
    }
    
    public void handleSearchMap(String attraction, String cityName, String description)
    {
        // Implement here
        System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
    }
    
    public void handleShowNumOfPurchases(String username) throws IOException
    {
//        sendToServer("#numOfPurchases " + username);
    }
    
    public void handleIncNumOfPurchases(String username) throws IOException
    {
//        sendToServer("#incNumOfPurchases " + username);
    }
    
    public void  handleStartConnection(String ip, int port) {       // Connecting to server

        setPort(port);
        setHost(ip);
        try {
            if (!isConnected()) {
                openConnection();
                System.out.println("You have connected server on port " + port);
//                Command command = new Command(new RegisterCommand(ANONYMOUS, ANONYMOUS, ANONYMOUS, ANONYMOUS), CommandType.RegisterCommand);
                Command command = new Command(new ConnectionCommand(), CommandType.ConnectionCommand);
                sendToServer(command);
            }
        } catch (IOException e) {
            System.out.println("Could not connect to the server.\nPlease recheck the server"
                    + " IP and the port.");
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
