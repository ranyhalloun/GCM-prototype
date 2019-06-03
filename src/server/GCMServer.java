package server;

import java.io.IOException;
import java.sql.SQLException;

import commands.Command;
import commands.CommandType;
import commands.RegisterCommand;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import database.Database;

public class GCMServer extends AbstractServer
{
    final private static String ANONYMOUS = "Anonymous";
    final private static int DEFAULT_PORT = 458;
    Database db;

    public GCMServer(int port) {
        super(port);
        db = new Database();
    }

    // Handle all commands that are coming from the client
    // client.setInfo saves info to the client (in a hashmap) you can get the info by using getInfo..
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Handling Message:");
        Command command = (Command) msg;
        CommandType type = command.getType();
        switch(type) {
            case ConnectionCommand:
                System.out.println("ConnectionCommand");
                client.setInfo("username", ANONYMOUS);
                break;
            case RegisterCommand:
                RegisterCommand registerCommand = command.getCommand(RegisterCommand.class);
                System.out.println("RegisterCommand");
                System.out.printf("Signing up:%nfirstname: %s, lastname: %s, username: %s, password: %s%n", 
                        registerCommand.getFirstname(), registerCommand.getLastname(), registerCommand.getUsername(), registerCommand.getPassword());
                client.setInfo("username", registerCommand.getUsername());
                break;
            case SigninCommand:
                System.out.println("SigninCommand");
                // client.setInfo("username", registerCommand.getUsername());
                break;
        default:
            break;
        }
    }
    
    protected void serverStarted()
    {
      System.out.println("Server listening for connections on port " + getPort());
    }
    
    protected void serverStopped()
    {
      System.out.println("Server has stopped listening for connections.");
    }
    
    protected void clientConnected(ConnectionToClient client) 
    {
      // display on server and clients that the client has connected.
      String msg = "A Client has connected";
      System.out.println(msg);
    }
    
    synchronized protected void clientDisconnected(ConnectionToClient client)
    {
        String msg = client.getInfo("username").toString() + " has disconnected";
        System.out.println(msg);
    }
    
    synchronized protected void clientException(ConnectionToClient client, Throwable exception) 
    {
        String msg = client.getInfo("username").toString() + " has disconnected";
        System.out.println(msg);
    }
    
    public void quit()
    {
        try {
            close();
        }
        catch(IOException e) {}
        System.exit(0);
    }
    
    public static void main(String[] args) 
    {
        int port = 0;

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        }
        catch(Throwable t) {
            port = DEFAULT_PORT; //Set port to 458
        }
      
        GCMServer sv = new GCMServer(port);
      
        try {
            sv.listen(); //Start listening for connections
        } 
        catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    
    }
}
