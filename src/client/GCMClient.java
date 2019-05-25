package client;

import ocsf.client.*;

import java.io.*;

import application.UIController;

// **This class overrides some of the methods defined in the abstract
// **superclass in order to give more functionality to the client.

public class GCMClient extends AbstractClient {

    String username;
    UIController uiController;
    
    public GCMClient(UIController uiController, String host, int port) throws IOException
    {
        super(host, port);
        this.username = "ANONYMOUS";
        this.uiController = uiController;
    }
    
    public void setUsername(String username) throws IOException
    {
        this.username = username;
        sendToServer("#login " + username);
    }

    @Override
    protected void handleMessageFromServer(Object msg)
    {
        if (msg.toString().startsWith("#numOfPurchases ")) { // Handling #numOfPurchases message from server
            String num = msg.toString().substring(16);
            if (Integer.parseInt(num) == -1) num = "NULL";
            uiController.showNumberToUser(num);
        } else {
            System.out.println(msg.toString());              // Other messages are printed to console
        }

    }
    
    public void handleShowNumOfPurchases(String username) throws IOException
    {
        sendToServer("#numOfPurchases " + username);
    }
    
    public void handleIncNumOfPurchases(String username) throws IOException
    {
        sendToServer("#incNumOfPurchases " + username);
    }
    
    public void  handleStartConnection(String ip, int port) {       // Connecting to server

        setPort(port);
        setHost(ip);
        try {
            if (!isConnected()) {
                openConnection();
                System.out.println("You have connected server on port " + port);
                sendToServer("#login ANONYMOUS");
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
