package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.SearchMapResult;
import Users.UserType;
import application.customer.Customer;
import commands.Command;
import commands.CommandType;
import commands.EditCustomerInfoCommand;
import commands.GetCitiesQueueCommand;
import commands.GetCustomerInfoCommand;
import commands.RegisterCommand;
import commands.RequestApprovalCommand;
import commands.SigninCommand;
import commands.SearchMapCommand;
import commands.InsertMapCommand;
import database.Database;
import javafx.collections.ObservableList;
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
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case SearchMapCommand:
                System.out.println(client.getInfo("username"));
            	handleSearchMapCommand(command, client);
            	try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	break;
            case InsertMapCommand:
            	handleInsertMapCommand(command, client);
            	try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	break;
            case GetCustomerInfoCommand:
            	handleGetCustomerInfoCommand(command, client);
            	try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	break;
            case EditCustomerInfoCommand:
            	handleEditCustomerInfoCommand(command, client);
            	try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            	break;
            case RequestApprovalCommand:
                handleRequestApprovalCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case GetCitiesQueueCommand:
                handleGetCitiesQueueCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                signinCommand.setSuccess(1);
                signinCommand.setRole(UserType.valueOf(db.getRole(username)));
                //System.out.println("Logged in successfully!\nRole: " + db.getRole(username));
                client.setInfo("username", username);
                client.setInfo("role", db.getRole(username));
            } else {
                //System.out.println("Logging in failed.");
                signinCommand.setSuccess(0);
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
    
    private void handleSearchMapCommand(Command command, ConnectionToClient client) 
    {
    	System.out.println("SearchMapCommand");
        SearchMapCommand searchMapCommand = command.getCommand(SearchMapCommand.class);
        String attraction = searchMapCommand.getAttraction();
        String cityName = searchMapCommand.getcityName();
        String description = searchMapCommand.getDescription();
        
       try {
           SearchMapResult result = db.searchMap(attraction, cityName, description);
           searchMapCommand.setSearchMapResult(result);
           searchMapCommand.setSuccess(true);
       } catch (SQLException e) {
           searchMapCommand.setSuccess(false);
           e.printStackTrace();
       }
       // sendToClient
       try {
           client.sendToClient(command);
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
    
    private void handleInsertMapCommand(Command command, ConnectionToClient client)
    {
    	System.out.println("InsertNewMapCommand");
        InsertMapCommand insertMapCommand = command.getCommand(InsertMapCommand.class);
        int mapID = insertMapCommand.getID();
        String cityName = insertMapCommand.getCityName();
        String description = insertMapCommand.getDescription();
        String imagePath = insertMapCommand.getImagePath();
        
        try {
    		if(db.insertNewMap(mapID, cityName, description, imagePath))
    		//System.out.println("Map inserted");
    			insertMapCommand.setSuccess(1);  
    		else
    			insertMapCommand.setSuccess(0);
           } catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
           }
    }
    
    private void handleGetCustomerInfoCommand(Command command, ConnectionToClient client)
    {
    	System.out.println("GetCustomerInfoCommand");
        GetCustomerInfoCommand getCustomerInfoCommand = command.getCommand(GetCustomerInfoCommand.class);
    	try {
    		Customer customer = db.getCustomerInfo(client.getInfo("username"));
    		//System.out.println("Map inserted");
			getCustomerInfoCommand.setSuccess(true);
			getCustomerInfoCommand.setUsername(customer.getUsername());
			getCustomerInfoCommand.setPassword(customer.getPassword());
			getCustomerInfoCommand.setFirstName(customer.getFirstName());
			getCustomerInfoCommand.setLastName(customer.getLastName());
			getCustomerInfoCommand.setEmail(customer.getEmail());
			getCustomerInfoCommand.setPhone(customer.getPhone());
			
           } catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
           }
    }
    
    private void handleEditCustomerInfoCommand(Command command, ConnectionToClient client)
    {
    	System.out.println("EditCustomerInfoCommand");
        EditCustomerInfoCommand editCustomerInfoCommand = command.getCommand(EditCustomerInfoCommand.class);
        Customer customer = editCustomerInfoCommand.getCustomer();
        String oldUsername = editCustomerInfoCommand.getNewUsername();
        try {
            if(db.editCustomerInfo(customer, oldUsername)) {
                editCustomerInfoCommand.setSuccess(true);
                client.setInfo("username", customer.getUsername());
            }
			else
				editCustomerInfoCommand.setSuccess(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void handleGetCitiesQueueCommand(Command command, ConnectionToClient client) {
        System.out.println("GetCitiesQueueCommand");
        GetCitiesQueueCommand getCitiesQueueCommand = command.getCommand(GetCitiesQueueCommand.class);
        try {
            getCitiesQueueCommand.setSuccess(true);
            getCitiesQueueCommand.setCities(db.getCitiesQueue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRequestApprovalCommand(Command command, ConnectionToClient client)
    {
        System.out.println("RequestApprovalCommand");
        RequestApprovalCommand requestApprovalCommand = command.getCommand(RequestApprovalCommand.class);
        String cityName = requestApprovalCommand.getCityName();
        try {
            if(db.requestApproval(cityName)) {
                requestApprovalCommand.setSuccess(true);
            }
            else
                requestApprovalCommand.setSuccess(false);
        } catch (SQLException e) {
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
