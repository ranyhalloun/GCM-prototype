package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import database.Database;

public class GCMServer extends AbstractServer
{

    final public static int DEFAULT_PORT = 458;
    Database db;

    public GCMServer(int port) {
        super(port);
        db = new Database();                                                        // TODO: Do we need this?
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) { // TODO: Handle other messages/inputs..
        if (msg.toString().startsWith("#login ")) {
            client.setInfo("username", msg.toString().substring(7));
            // TODO: Add password.
        }
        else if (msg.toString().startsWith("#numOfPurchases ")) {
            String username = msg.toString().substring(16);
                try {
                    client.sendToClient("#numOfPurchases " + db.getNumOfPurchases(username));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        else if(msg.toString().startsWith("#incNumOfPurchases ")) {
            String username = msg.toString().substring(19);
            try {
                db.incNumOfPurchases(username);
                client.sendToClient("#numOfPurchases " + db.getNumOfPurchases(username));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
