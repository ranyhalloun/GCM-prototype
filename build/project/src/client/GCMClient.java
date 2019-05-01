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
        openConnection();
        this.username = "ANONYMOUS";
        sendToServer("#login ANONYMOUS");
        this.uiController = uiController;
    }
    
    public void setUsername(String username) throws IOException
    {
        this.username = username;
        sendToServer("#login " + username);
    }

    @Override
    protected void handleMessageFromServer(Object msg)      // TODO: handle messages.. 
    {
        if (msg.toString().startsWith("#numOfPurchases ")) {
            String num = msg.toString().substring(16);
            uiController.showNumberToUser(num);
        } else {
            System.out.println(msg.toString());
        }

    }

//    public void handleInputFromUser(String input)         // TODO: handle every input from user..
//    {
//        
//    }
    
    public void handleShowNumOfPurchases(String username) throws IOException
    {
        sendToServer("#numOfPurchases " + username);
    }
    
    public void handleIncNumOfPurchases(String username) throws IOException
    {
        sendToServer("#incNumOfPurchases " + username);
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
