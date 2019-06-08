package server;

import java.io.IOException;
import java.sql.SQLException;

import Users.UserType;
import commands.Command;
import commands.CommandType;
import commands.RegisterCommand;
import commands.SigninCommand;
import database.Database;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


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
                handleRegisterCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case SigninCommand:
                handleSigninCommand(command, client);
                break;
        default:
            break;
        }
    }

    private void handleRegisterCommand(Command command, ConnectionToClient client) {
    	System.out.println("RegisterCommand");
        RegisterCommand registerCommand = command.getCommand(RegisterCommand.class);
        String firstname = registerCommand.getFirstname();
        String lastname = registerCommand.getLastname();
        String username = registerCommand.getUsername();
        String password = registerCommand.getPassword();
        String email = registerCommand.getEmail();
        String phone = registerCommand.getPhone();
        System.out.printf("Signing up:%nfirstname: %s, lastname: %s, username: %s, password: %s, email: %s, phone: %s%n", firstname, lastname, username, password, email, phone);
        client.setInfo("username", username);
              
        try {
        	if (db.usernameExist(username))
        	    registerCommand.setSuccess(0);
        	else {
    			db.registerNewCustomer(firstname, lastname, username, password, email, phone);
                registerCommand.setSuccess(1);
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
            client.sendToClient(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleSigninCommand(Command command, ConnectionToClient client) {
        System.out.println("server: handling SigninCommand");
        SigninCommand signinCommand = command.getCommand(SigninCommand.class);
        String username = signinCommand.getUsername();
        String password = signinCommand.getPassword();
        // authenticate
        try {
            if (db.authenticate(username, password)) {
                signinCommand.setSuccess(true);
                signinCommand.setRole(UserType.valueOf(db.getRole(username)));
                System.out.println("Logged in successfully!\nRole: " + db.getRole(username));
                client.setInfo("username", username);
                client.setInfo("role", db.getRole(username));
            } else {
                System.out.println("Logging in failed.");
                signinCommand.setSuccess(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // sendToClient
        try {
            client.sendToClient(command);
        } catch (IOException e) {
            e.printStackTrace();
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
