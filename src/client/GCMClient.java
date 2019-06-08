package client;

import ocsf.client.*;

import java.io.*;

import application.Main;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
import commands.RegisterCommand;
import commands.SigninCommand;
import commands.SearchMapCommand;
import commands.InsertMapCommand;

// **This class overrides some of the methods defined in the abstract
// **superclass in order to give more functionality to the client.

public class GCMClient extends AbstractClient {
    
    public GCMClient(String host, int port) {
        super(host, port);
    }

    public void handleRegistration(String firstname, String lastname, String username, String password, String email, String phone) throws IOException
    {
        System.out.println("handleRegistration");
        Command command = new Command(new RegisterCommand(firstname, lastname, username, password, email, phone), CommandType.RegisterCommand);
        sendToServer(command);
    }
    

    public void handleSignIn(String username, String password) throws IOException {
        System.out.println("handleSignin");
        Command command = new Command(new SigninCommand(username, password), CommandType.SigninCommand);
        sendToServer(command);
    }
    
    public void handleInsertNewMap(int id, String cityName, String description, String imagePath) throws IOException
    {
    	System.out.println("handleInsertNewMap");
    	Command command = new Command(new InsertMapCommand(id, cityName, description, imagePath), CommandType.InsertMapCommand);;
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
//    	if(msg.toString().equals("username exists!")) {
//    		Main.getInstance().error(msg.toString());
//    	}
    	
    	
    	System.out.println("Handling Message from server:");
        Command command = (Command) msg;
        CommandType type = command.getType();
        switch(type) {
            case RegisterCommand:
			try {
				Main.getInstance().error(command.getCommand(RegisterCommand.class).getError());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case SigninCommand:
                //add handling message
            	if(command.getCommand(SigninCommand.class).getRole().equals("C"))
					try {
						Main.getInstance().goToSearchMapResult("","","");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                break;
        default:
            break;
        }
        
    }
    
    public void handleSearchMap(String attraction, String cityName, String description) throws IOException
    {
        // Implement here
        System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
        Command command = new Command(new SearchMapCommand(attraction, cityName, description), CommandType.SearchMapCommand);
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
