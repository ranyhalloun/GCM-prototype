package server;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.Attraction;
import Entities.City;
import Entities.Map;
import Entities.SearchMapResult;
import Entities.Tour;
import Users.UserType;
import application.customer.Customer;
import commands.AddAttractionToTourCommand;
import commands.AddTourToCityCommand;
import commands.Command;
import commands.CommandType;
import commands.EditCustomerInfoCommand;
import commands.GetAttractionsOfCityCommand;
import commands.GetCitiesQueueCommand;
import commands.GetCityReportCommand;
import commands.GetCityToursCommand;
import commands.GetCustomerInfoCommand;
import commands.GetCustomersCitiesCommand;
import commands.GetDownloadsCommand;
import commands.GetMapInfoFromIDCommand;
import commands.GetTourInfoFromIDCommand;
import commands.GetViewsCommand;
import commands.RegisterCommand;
import commands.RemoveAttractionFromTourCommand;
import commands.RemoveTourFromCityToursCommand;
import commands.RequestApprovalCommand;
import commands.SearchCityCommand;
import commands.SigninCommand;
import commands.SearchMapCommand;
import commands.InsertMapCommand;
import commands.CheckCityExistanceCommand;
import commands.CheckCustomerCommand;
import commands.GetNewExternalMapsCommand;
import commands.GetOldPricesCommand;
import commands.GetPricesCommand;
import commands.GetPurchasesCommand;
import commands.UpdateDBAfterAcceptCommand;
import commands.UpdateDBAfterDeclineCommand;
import commands.UpdatePricesAfterAcceptCommand;
import commands.SendNewPricesCommand;
import commands.InsertNewCityCommand;
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
            case GetMapInfoFromIDCommand:
                handleGetMapInfoFromIDCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case SearchCityCommand:
                System.out.println(client.getInfo("username"));
                handleSearchCityCommand(command, client);
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
                
            case GetCityToursCommand:
                handleGetCityToursCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case GetTourInfoFromIDCommand:
                handleGetTourInfoFromIDCommand(command, client);
                try {
                    client.sendToClient(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RemoveAttractionFromTourCommand:
				try {
					handleRemoveAttractionFromTourCommand(command, client);
					client.sendToClient(command);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                break;
            case RemoveTourFromCityToursCommand:
                try {
                    handleRemoveTourFromCityToursCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case GetAttractionsOfCityCommand:
				try {
					handleGetAttractionsOfCityCommand(command, client);
					client.sendToClient(command);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                break;
            case AddAttractionToTourCommand:
				try {
					handleAddAttractionToTourCommand(command, client);
					client.sendToClient(command);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                break;
            case AddTourToCityCommand:
                try {
                    handleAddTourToCityCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UpdateDBAfterDeclineCommand:
                try {
                    handleUpdateDBAfterDeclineCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case UpdateDBAfterAcceptCommand:
                try {
                    handleUpdateDBAfterAcceptCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case GetNewExternalMapsCommand:
                try {
                    handleGetNewExternalMapsCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case CheckCityExistanceCommand:
                try {
                    handleCheckCityExistanceCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case InsertNewCityCommand:
                try {
                    handleInsertNewCityCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case GetOldPricesCommand:
                try {
                    handleGetOldPricesCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case SendNewPricesCommand:
                try {
                    handleSendNewPricesCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case GetPricesCommand:
                try {
                    handleGetPricesCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UpdatePricesAfterAcceptCommand:
                try {
                    handleUpdatePricesAfterAcceptCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UpdatePricesAfterDeclineCommand:
                try {
                    handleUpdatePricesAfterDeclineCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
                
            case GetCustomersCitiesCommand:
                try {
                    handleGetCustomersCitiesCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
                
            case  GetCityReportCommand:
                try {
                    handleGetCityReportCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case  CheckCustomerCommand:
                try {
                    handleCheckCustomerCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case  GetPurchasesCommand:
                try {
                    handleGetPurchasesCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case  GetViewsCommand:
                try {
                    handleGetViewsCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case  GetDownloadsCommand:
                try {
                    handleGetDownloadsCommand(command, client);
                    client.sendToClient(command);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
          default:
            break;
        }
    }

    private void handleGetMapInfoFromIDCommand(Command command, ConnectionToClient client) {
        Map map = new Map();
        System.out.println("handleGetMapInfoFromIDCommand in server");
        GetMapInfoFromIDCommand getMapInfoFromIDCommand = command.getCommand(GetMapInfoFromIDCommand.class);
        int mapID = getMapInfoFromIDCommand.getMapID();
        map = db.findMapByMapID(mapID);
        getMapInfoFromIDCommand.setMap(map);
        getMapInfoFromIDCommand.setSuccess(true);
        // sendToClient
        try {
            client.sendToClient(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetTourInfoFromIDCommand(Command command, ConnectionToClient client) {
        System.out.println("handleGetTourInfoFromIDCommand In Server");
        GetTourInfoFromIDCommand getTourInfoFromIDCommand = command.getCommand(GetTourInfoFromIDCommand.class);
        int tourID = getTourInfoFromIDCommand.getTourID();
        Tour tour = db.getTourInfoByID(tourID);
        getTourInfoFromIDCommand.setSuccess(true);
        getTourInfoFromIDCommand.setTour(tour);
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
                //System.out.println("Logged in successfully!\nRole: " + db.getRole(username));
                client.setInfo("username", username);
                client.setInfo("role", db.getRole(username));
            } else {
                //System.out.println("Logging in failed.");
            	signinCommand.setError("(Username,Password) doesn't exist!");
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
    
    private void handleSearchCityCommand(Command command, ConnectionToClient client) {
        System.out.println("SearchCityCommand In Server");
        SearchCityCommand searchCityCommand = command.getCommand(SearchCityCommand.class);
        String cityName = searchCityCommand.getcityName();

        City city = db.searchCity(cityName);
        searchCityCommand.setCity(city);
        searchCityCommand.setSuccess(true);
        
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
           ArrayList<Map> maps = db.searchMaps(attraction, cityName, description);
           searchMapCommand.setMaps(maps);
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
        Map map = insertMapCommand.getMap();
        
        try {
            if(db.insertNewMap(map)) {
                insertMapCommand.setSuccess(true);
            }   
            else {
                insertMapCommand.setSuccess(false);
            }
           } catch (SQLException e) {
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
            requestApprovalCommand.setSuccess(db.requestApproval(cityName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleGetCityToursCommand(Command command, ConnectionToClient client)
    {
    	 System.out.println("GetCityToursCommand");
    	 GetCityToursCommand getCityToursCommand = command.getCommand(GetCityToursCommand.class);
         String cityName = getCityToursCommand.getCityName();
         ArrayList<Tour> tours = db.getToursOfCity(cityName);
         getCityToursCommand.setSuccess(true);
         getCityToursCommand.setTours(tours);
    }
    
    private void handleRemoveAttractionFromTourCommand(Command command, ConnectionToClient client) throws SQLException {
    	System.out.println("RemoveAttractionFromTourCommand");
    	RemoveAttractionFromTourCommand removeAttractionFromTourCommand = command.getCommand(RemoveAttractionFromTourCommand.class);
        String attractionID = removeAttractionFromTourCommand.getAttractionID();
        int tourID = removeAttractionFromTourCommand.getTourID();
        db.removeAttractionFromTour(attractionID, tourID);
        removeAttractionFromTourCommand.setSuccess(true);
    }
    
    private void handleRemoveTourFromCityToursCommand(Command command, ConnectionToClient client) throws SQLException {
        System.out.println("RemoveTourFromCityToursCommand");
        RemoveTourFromCityToursCommand removeTourFromCityToursCommand = command.getCommand(RemoveTourFromCityToursCommand.class);
        int tourID = removeTourFromCityToursCommand.getTourID();
        db.removeTourFromCityTours(tourID);
        removeTourFromCityToursCommand.setSuccess(true);
    }
    
    private void handleGetAttractionsOfCityCommand(Command command, ConnectionToClient client) throws SQLException {
    	System.out.println("handleGetAttractionsOfCityCommand");
    	GetAttractionsOfCityCommand getAttractionsOfCityCommand  = command.getCommand(GetAttractionsOfCityCommand.class);
        int tourID = getAttractionsOfCityCommand.getTourID();
    	String cityName = getAttractionsOfCityCommand.getCityName();
        getAttractionsOfCityCommand.setAttractions(db.getAttractionsOfCity(cityName, tourID));
        getAttractionsOfCityCommand.setSuccess(true);
    }
    
    private void handleAddAttractionToTourCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleAddAttractionToTourCommand");
    	AddAttractionToTourCommand addAttractionToTourCommand  = command.getCommand(AddAttractionToTourCommand.class);
        String attractionID = addAttractionToTourCommand.getAttractionId();
        int tourID = addAttractionToTourCommand.getTourID();
        int time = addAttractionToTourCommand.getTime();
        String cityName = addAttractionToTourCommand.getCityName();
        db.addAttractionToTour(attractionID, tourID, time, cityName);
        addAttractionToTourCommand.setSuccess(true);
    }
    
    private void handleAddTourToCityCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleAddTourToCityCommand");
        AddTourToCityCommand addTourToCityCommand  = command.getCommand(AddTourToCityCommand.class);
        String cityName = addTourToCityCommand.getCityName();
        String description = addTourToCityCommand.getDescription();
        db.addTourToCity(cityName, description);
        addTourToCityCommand.setSuccess(true);
    }
    
    private void handleUpdateDBAfterDeclineCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleUpdateDBAfterDeclineCommnad");
        UpdateDBAfterDeclineCommand updateDBAfterDeclineCommand  = command.getCommand(UpdateDBAfterDeclineCommand.class);
        String cityName = updateDBAfterDeclineCommand.getCityName();
        db.updateDBAfterDecline(cityName);
        updateDBAfterDeclineCommand.setSuccess(true);
    }

    private void handleUpdateDBAfterAcceptCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleUpdateDBAfterAcceptCommand");
        UpdateDBAfterAcceptCommand updateDBAfterAcceptCommand  = command.getCommand(UpdateDBAfterAcceptCommand.class);
        String cityName = updateDBAfterAcceptCommand.getCityName();
        db.updateDBAfterAccept(cityName);
        updateDBAfterAcceptCommand.setSuccess(true);
    }

    private void handleGetNewExternalMapsCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleGetNewExternalMapsCommand");
        GetNewExternalMapsCommand getNewExternalMapsCommand  = command.getCommand(GetNewExternalMapsCommand.class);
        getNewExternalMapsCommand.setMaps(db.getNewExternalMaps());
        getNewExternalMapsCommand.setSuccess(true);
    }

    private void handleCheckCityExistanceCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleCheckCityExistanceCommand");
        CheckCityExistanceCommand checkCityExistanceCommand  = command.getCommand(CheckCityExistanceCommand.class);
        String cityName = command.getCommand(CheckCityExistanceCommand.class).getCityName();
        checkCityExistanceCommand.setSuccess(db.cityExist(cityName));
    }

    private void handleInsertNewCityCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleInsertNewCityCommand");
        InsertNewCityCommand insertNewCityCommand  = command.getCommand(InsertNewCityCommand.class);
        String cityName = command.getCommand(InsertNewCityCommand.class).getCityName();
        String description = command.getCommand(InsertNewCityCommand.class).getDescription();
        insertNewCityCommand.setSuccess(db.insertNewCity(cityName, description));
    }

    private void handleGetOldPricesCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleGetOldPricesCommand");
        GetOldPricesCommand getOldPricesCommand  = command.getCommand(GetOldPricesCommand.class);
        ArrayList<String> prices = db.getCurrentPrices();
        getOldPricesCommand.setSubscriptionPrice(prices.get(0));
        getOldPricesCommand.setOneTimePurchasePrice(prices.get(1));
    }

    private void handleSendNewPricesCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleSendNewPricesCommand");
        SendNewPricesCommand sendNewPricesCommand  = command.getCommand(SendNewPricesCommand.class);
        String newOnePrice = command.getCommand(SendNewPricesCommand.class).getNewOnePrice();
        String newSubsPrice = command.getCommand(SendNewPricesCommand.class).getNewSubsPrice();

        db.sendNewPrices(newOnePrice, newSubsPrice);
    }

    private void handleGetPricesCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleGetPricesCommand");
        GetPricesCommand getPricesCommand  = command.getCommand(GetPricesCommand.class);
        String oldOnePrice = command.getCommand(GetPricesCommand.class).getOldOne();
        String oldSubsPrice = command.getCommand(GetPricesCommand.class).getOldSubs();
        String newOnePrice = command.getCommand(GetPricesCommand.class).getNewOne();
        String newSubsPrice = command.getCommand(GetPricesCommand.class).getNewSubs();

        ArrayList<String> prices = db.getPrices(oldSubsPrice, oldOnePrice, newSubsPrice, newOnePrice);
        getPricesCommand.setOldSubs(prices.get(0));
        getPricesCommand.setOldOne(prices.get(1));
        getPricesCommand.setNewSubs(prices.get(2));
        getPricesCommand.setNewOne(prices.get(3));
    }

    private void handleUpdatePricesAfterAcceptCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleUpdatePricesAfterAcceptCommand");
        String newSubsPrice = command.getCommand(UpdatePricesAfterAcceptCommand.class).getNewSubsPrice();
        String newOnePrice = command.getCommand(UpdatePricesAfterAcceptCommand.class).getNewOnePrice();
        db.updatePricesAfterAccept(newSubsPrice, newOnePrice);
    }

    private void handleUpdatePricesAfterDeclineCommand(Command command, ConnectionToClient client) throws SQLException{
        System.out.println("handleUpdatePricesAfterDeclineCommand");
        db.updatePricesAfterDecline();
    }
    
    private void handleGetCustomersCitiesCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleGetCustomersCitiesCommand");
    	GetCustomersCitiesCommand getCustomersCitiesCommand  = command.getCommand(GetCustomersCitiesCommand.class);
    	
    	getCustomersCitiesCommand.setCities(db.getCustomersCities());
    }
    
    private void handleGetCityReportCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleGetCityReportCommand");
    	GetCityReportCommand getCityReportCommand  = command.getCommand(GetCityReportCommand.class);
    	String cityName = getCityReportCommand.getCityName();
    	LocalDate fromDate = getCityReportCommand.getFromDate();
    	LocalDate toDate = getCityReportCommand.getToDate();
    	getCityReportCommand.setReport(db.getCityReport(cityName, fromDate, toDate));
    }
    
    private void handleCheckCustomerCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleCheckCustomerCommand");
    	CheckCustomerCommand checkCustomerCommand  = command.getCommand(CheckCustomerCommand.class);
    	String username = checkCustomerCommand.getUsername();
    	checkCustomerCommand.setExists(db.customerExists(username));
    }
    
    private void handleGetPurchasesCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleGetPurchasesCommand");
    	GetPurchasesCommand getPurchasesCommand  = command.getCommand(GetPurchasesCommand.class);
    	String customerUsername = getPurchasesCommand.getCustomerUsername();
    	getPurchasesCommand.setPurchases((db.getPurchases(customerUsername)));
    }
    
    private void handleGetViewsCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleGetViewsCommand");
    	GetViewsCommand getViewsCommand  = command.getCommand(GetViewsCommand.class);
    	String customerUsername = getViewsCommand.getCustomerUsername();
    	getViewsCommand.setViews((db.getViews(customerUsername)));
    }
    
    private void handleGetDownloadsCommand(Command command, ConnectionToClient client) throws SQLException{
    	System.out.println("handleGetDownloadsCommand");
    	GetDownloadsCommand getDownloadsCommand  = command.getCommand(GetDownloadsCommand.class);
    	String customerUsername = getDownloadsCommand.getCustomerUsername();
    	getDownloadsCommand.setDownloads((db.getDownloads(customerUsername)));
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
