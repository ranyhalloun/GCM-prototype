package client;

import ocsf.client.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

import Entities.Attraction;
import Entities.Map;
import Entities.StringIntPair;

import java.util.ArrayList;

import Users.UserType;
import application.Main;
import application.arrayOfStrings;
import application.boolObject;
import application.customer.Customer;
import commands.AddAttractionToTourCommand;
import commands.CheckCityExistanceCommand;
import commands.Command;
import commands.CommandType;
import commands.ConnectionCommand;
import commands.EditCustomerInfoCommand;
import commands.GetAttractionsOfCityCommand;
import commands.GetCitiesQueueCommand;
import commands.GetCityToursCommand;
import commands.GetCustomerInfoCommand;
import commands.GetNewExternalMapsCommand;
import commands.GetOldPricesCommand;
import commands.GetPricesCommand;
import commands.InsertMapCommand;
import commands.InsertNewCityCommand;
import commands.RegisterCommand;
import commands.RemoveAttractionFromTourCommand;
import commands.RequestApprovalCommand;
import commands.SearchMapCommand;
import commands.SendNewPricesCommand;
import commands.SigninCommand;
import commands.UpdateDBAfterAcceptCommand;
import commands.UpdateDBAfterDeclineCommand;
import commands.UpdatePricesAfterAcceptCommand;
import commands.UpdatePricesAfterDeclineCommand;
import javafx.collections.ObservableList;
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
            Main.getInstance().goToRegistration("Username exists. Please choose another username");
            break;
        case 1:
            System.out.println("Registration completed!");
            Main.getInstance().goToLogin("");
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
        if(success) {
        	userType = command.getCommand(SigninCommand.class).getRole();
            Main.getInstance().updateUserType(userType);
            System.out.println("You have successfully signed in!");
            System.out.println("Role: " + userType);
            switch(userType) {
                case Anonymous:
                case Worker:
                    Main.getInstance().goToSearchMap("");
                    break;
                case Customer:
                	Main.getInstance().goToCostumerServices();
                	break;
                case GCMWorker:
                    Main.getInstance().goToGCMWorkerServices();
                    break;
                case GCMManager:
                    Main.getInstance().goToGCMManagerServices();
                    break;
                case CompanyManager:
                    Main.getInstance().goToCompanyManagerServices();
                    break;
            }
        }
        else {
            String error = command.getCommand(SigninCommand.class).getError();
        	Main.getInstance().goToLogin(error);
            System.out.println("Sign in failed.");
        }
    }

    public void handleGetCityTours(String cityName) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleSearchMap");
        Command command = new Command(new GetCityToursCommand(cityName), CommandType.GetCityToursCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetCityToursFromServer();
    }

    public void handleGetCityToursFromServer() throws IOException{
    	System.out.println("handleGetCityToursFromServer");
        boolean success = command.getCommand(GetCityToursCommand.class).getSuccess();
        
        if(success) {
            System.out.println("City tours info got successfully!");
            Main.getInstance().goToCityToursTable(command.getCommand(GetCityToursCommand.class).getTours(), command.getCommand(GetCityToursCommand.class).getCityName());
        }
        else {
            System.out.println("There is a problem in the database connection");
        }
    }
    
    public void handleSearchMap(String attraction, String cityName, String description) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleSearchMap");
    	System.out.printf("Searching map: attraction: %s, cityName: %s, description: %s%n", attraction, cityName, description);
        Command command = new Command(new SearchMapCommand(attraction, cityName, description), CommandType.SearchMapCommand);
        sendToServer(command);
        waitForServerResponse();
        handleSearchMapFromServer();
    }
    
    public void handleSearchMapFromServer() throws IOException {
    	System.out.println("handleSearchMapFromServer");
        boolean success = command.getCommand(SearchMapCommand.class).getSuccess();
        if (!success) {
            System.out.println("Error occured.");
            return;
        }
        boolean searchByCity = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByCity();
        boolean searchByAttraction = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByAttraction();
        boolean searchByDescription = command.getCommand(SearchMapCommand.class).getSearchMapResult().getSearchByDescription();
        if(searchByCity&&!searchByAttraction&&!searchByDescription)
        	Main.getInstance().cityInfo(command.getCommand(SearchMapCommand.class).getSearchMapResult());
        
        else if(!searchByCity&&searchByAttraction&&!searchByDescription)
        	Main.getInstance().attractionInfo(command.getCommand(SearchMapCommand.class).getSearchMapResult());
        else
        	Main.getInstance().goToMapsTable(command.getCommand(SearchMapCommand.class).getSearchMapResult());
    }
    
    public void handleInsertNewMap(Map map, ArrayList<Map> maps) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleInsertNewMap");
        Command command = new Command(new InsertMapCommand(map), CommandType.InsertMapCommand);;
        sendToServer(command);
        waitForServerResponse();
        maps.remove(map);
        handleInsertNewMapFromServer(maps);
    }
    
    public void handleInsertNewMapFromServer(ArrayList<Map> maps) throws IOException {
    	System.out.println("handleInsertNewMapFromServer");
        boolean success = command.getCommand(InsertMapCommand.class).getSuccess();
        Main.getInstance().goToExternalMaps(maps, "Map inserted", "");
    }
    
    public void handleCheckCityExistance(String cityName, Map map, ArrayList<Map> maps) throws IOException{
    	commandRequest = true;
        System.out.println("handleCheckCityExistance");
        Command command = new Command(new CheckCityExistanceCommand(cityName), CommandType.CheckCityExistanceCommand);;
        sendToServer(command);
        waitForServerResponse();
        handleCheckCityExistanceFromServer(map, maps);
    }
    
    public void handleCheckCityExistanceFromServer(Map map, ArrayList<Map> maps) throws IOException{
    	System.out.println("handleCheckCityExistanceFromServer");
        boolean success = command.getCommand(CheckCityExistanceCommand.class).getSuccess();
        String cityName = command.getCommand(CheckCityExistanceCommand.class).getCityName();
        System.out.println(success);
        if(!success) {
        	System.out.println("Create City");
        	Main.getInstance().goToExternalMaps(maps, "", "City doesn't exist!");
        }
        else
            Main.getInstance().insertNewMap(map, maps);
    }
    
    public void handleGetCustomerInfo(Customer customer) throws IOException
    {
    	
    	commandRequest = true;
        System.out.println("handleGetCustomerInfo");
        Command command = new Command(new GetCustomerInfoCommand(), CommandType.GetCustomerInfoCommand);;
        sendToServer(command);
        waitForServerResponse();
        handleGetCustomerInfoFromServer(customer);
        System.out.println(customer.getPassword());

    }
    
    public void handleGetCustomerInfoFromServer(Customer customer) {
    	System.out.println("handleGetCustomerInfoFromServer");
        boolean success = command.getCommand(GetCustomerInfoCommand.class).getSuccess();
        customer.setUsername(command.getCommand(GetCustomerInfoCommand.class).getUsername());
        customer.setPassword(command.getCommand(GetCustomerInfoCommand.class).getPassword());
        customer.setFirstName(command.getCommand(GetCustomerInfoCommand.class).getFirstName());
        customer.setLastName(command.getCommand(GetCustomerInfoCommand.class).getLastName());
        customer.setEmail(command.getCommand(GetCustomerInfoCommand.class).getEmail());
        customer.setPhone(command.getCommand(GetCustomerInfoCommand.class).getPhone());
        if(success)
            System.out.println("Customer info got successfully!");
        else
            System.out.println("There is a problem in the database connection");
    }
    
    public void handleGetCitiesQueue(arrayOfStrings cities) throws IOException {
        commandRequest = true;
        System.out.println("handleGetCitiesQueue");
        Command command = new Command(new GetCitiesQueueCommand(), CommandType.GetCitiesQueueCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetCitiesQueueFromServer(cities);
    }
    
    public void handleGetCitiesQueueFromServer(arrayOfStrings cities) {
        System.out.println("handleGetCitiesQueueFromServer");
        boolean success = command.getCommand(GetCitiesQueueCommand.class).getSuccess();
        cities.setArrayList(command.getCommand(GetCitiesQueueCommand.class).getCities());
        if(success)
            System.out.println("Cities info got successfully!");
        else
            System.out.println("There is a problem in the database connection");
    }
    
    public void handleEditingCustomerInfo(Customer customer, String oldUsername, boolObject exists) throws IOException
    {
    	commandRequest = true;
        System.out.println("handleEditingCusotmerInfo");
        Command command = new Command(new EditCustomerInfoCommand(customer, oldUsername), CommandType.EditCustomerInfoCommand);
        sendToServer(command);
        waitForServerResponse();
        handleEditingCustomerInfoFromServer(customer, exists);
    }
    
    public void handleEditingCustomerInfoFromServer(Customer customer, boolObject exists) {
        System.out.println("handleEditingCustomerInfoFromServer");
        exists.setValue(!command.getCommand(EditCustomerInfoCommand.class).getSuccess());
        if (!exists.getValue())
            System.out.println("Customer info updated successfully!");
        else
            System.out.println("User name exists!");
    }

    public void handleRequestApproval(String cityName) throws IOException {
        commandRequest = true;
        System.out.println("handleRequestApproval");
        Command command = new Command(new RequestApprovalCommand(cityName), CommandType.RequestApprovalCommand);
        sendToServer(command);
        waitForServerResponse();
        handleRequestApprovalFromServer(cityName);
    }

    public void handleRequestApprovalFromServer(String cityName) {
        System.out.println("handleEditingCustomerInfoFromServer");
        int success = command.getCommand(RequestApprovalCommand.class).getSuccess();
        switch(success){
        	case 1: 
        		System.out.println("Request have sent successfully!");
        		break;
        	case -1:
				try {
					Main.getInstance().goToRequestApproval("CityName doesn't exist!");
				} catch (IOException e) {
					e.printStackTrace();
				}
        		break;
        	default: 
	        	try {
					Main.getInstance().goToRequestApproval("CityName already sent!");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
    }
    
    public void handleRemoveAttractionFromTour(String attractionName, int tourID) throws IOException {
    	commandRequest = true;
        System.out.println("handleRemoveAttractionFromTour");
        Command command = new Command(new RemoveAttractionFromTourCommand(attractionName, tourID), CommandType.RemoveAttractionFromTourCommand);
        sendToServer(command);
        waitForServerResponse();
        handleRemoveAttractionFromTourCommandFromServer();
    }
    
    public void handleRemoveAttractionFromTourCommandFromServer() {
        System.out.println("handleRemoveAttractionFromTourCommandFromServer");
        boolean success = command.getCommand(RemoveAttractionFromTourCommand.class).getSuccess();
        if (success)
            System.out.println("Attraction have been removed successfully!");
        else
            System.out.println("Failed removing the attraction from tour!!");
    }
    public void handleGetAttractionsOfCity(String cityName, int tourID) throws IOException {
    	commandRequest = true;
        System.out.println("handleGetAttractionsOfCity");
        Command command = new Command(new GetAttractionsOfCityCommand(cityName, tourID), CommandType.GetAttractionsOfCityCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetAttractionsOfCityCommandFromServer();
    }
    
    public void handleGetAttractionsOfCityCommandFromServer() throws IOException {
        System.out.println("handleGetAttractionsOfCityCommandFromServer");
        boolean success = command.getCommand(GetAttractionsOfCityCommand.class).getSuccess();
        ArrayList<Attraction> attractions = command.getCommand(GetAttractionsOfCityCommand.class).getAttractions();
        int tourID = command.getCommand(GetAttractionsOfCityCommand.class).getTourID();
        Main.getInstance().goToAddAttractionToTour(attractions, "", tourID);
        if (success)
            System.out.println("Attractions have been brought successfully!");
        else
            System.out.println("Failed to get the attractionOfCity!!");
    }
    
    public void handleAddAttractionsToTour(String attractionID, int tourID, int time) throws IOException {
    	commandRequest = true;
        System.out.println("handleAddAttractionsToTour");
        Command command = new Command(new AddAttractionToTourCommand(attractionID, tourID, time), CommandType.AddAttractionToTourCommand);
        sendToServer(command);
        waitForServerResponse();
        handleAddAttractionToTourCommandFromServer();
    }
    
    public void handleAddAttractionToTourCommandFromServer() {
    	System.out.println("handleAddAttractionToTourCommandFromServer");
        boolean success = command.getCommand(AddAttractionToTourCommand.class).getSuccess();
        if (success)
            System.out.println("Attractions added successfully to tour!");
        else
            System.out.println("Failed to add attraction to tour!");
    }
    
    public void handleUpdateDBAfterDecline(String cityName) throws IOException {
    	commandRequest = true;
        System.out.println("handleUpdateDBAfterDecline");
        Command command = new Command(new UpdateDBAfterDeclineCommand(cityName), CommandType.UpdateDBAfterDeclineCommand);
        sendToServer(command);
        waitForServerResponse();
        handleUpdateDBAfterDeclineCommandFromServer();
    }
    
    public void handleUpdateDBAfterDeclineCommandFromServer() {
    	System.out.println("handleUpdateDBAfterDeclineCommandFromServer");
        boolean success = command.getCommand(UpdateDBAfterDeclineCommand.class).getSuccess();
        if (success)
            System.out.println("DB updated successfully after decline!");
        else
            System.out.println("Failed to update DB after decline!");
    }
    
    public void handleUpdateDBAfterAccept(String cityName) throws IOException {
    	commandRequest = true;
        System.out.println("handleUpdateDBAfterAccept");
        Command command = new Command(new UpdateDBAfterAcceptCommand(cityName), CommandType.UpdateDBAfterAcceptCommand);
        sendToServer(command);
        waitForServerResponse();
        handleUpdateDBAfterAcceptCommandFromServer();
    }
    
    public void handleUpdateDBAfterAcceptCommandFromServer() {
    	System.out.println("handleUpdateDBAfterAcceptCommandFromServer");
        boolean success = command.getCommand(UpdateDBAfterAcceptCommand.class).getSuccess();
        if (success)
            System.out.println("DB updated successfully after accept!");
        else
            System.out.println("Failed to update DB after accept!");
    }
    
    public void handleGetNewExternalMaps() throws IOException{
    	commandRequest = true;
        System.out.println("handleGetNewExternalMaps");
        Command command = new Command(new GetNewExternalMapsCommand(), CommandType.GetNewExternalMapsCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetNewExternalMapsCommandFromServer();
    }
    
    public void handleGetNewExternalMapsCommandFromServer() throws IOException {
    	System.out.println("handleGetExternalMapsCommandFromServer");
        boolean success = command.getCommand(GetNewExternalMapsCommand.class).getSuccess();
        ArrayList<Map> maps = command.getCommand(GetNewExternalMapsCommand.class).getMaps();
        if (success) {
            System.out.println("External maps got successfully!");
            Main.getInstance().goToExternalMaps(maps, "", "");        }
        else
            System.out.println("Failed to get external maps!");
    }
    
    public void handleInsertNewCity(ArrayList<Map> maps, String cityName, String description) throws IOException {
    	commandRequest = true;
        System.out.println("handleInsertNewCity");
        Command command = new Command(new InsertNewCityCommand(cityName, description), CommandType.InsertNewCityCommand);
        sendToServer(command);
        waitForServerResponse();
        handleInsertNewCityFromServer(maps);
    }
    
    public void handleInsertNewCityFromServer(ArrayList<Map> maps) throws IOException {
    	System.out.println("handleInsertNewCityFromServer");
        boolean success = command.getCommand(InsertNewCityCommand.class).getSuccess();
        if (success) {
            Main.getInstance().goToInsertNewCity(maps, "", "City inserted");        }
        else
        	Main.getInstance().goToInsertNewCity(maps, "City Exists", "");
    }
    
    public void handleChangePrices() throws IOException {
    	commandRequest = true;
        System.out.println("handleChangePrices");
        Command command = new Command(new GetOldPricesCommand(), CommandType.GetOldPricesCommand);
        sendToServer(command);
        waitForServerResponse();
        handleChangePricesFromServer();
    }
    
    public void handleChangePricesFromServer() throws IOException{
    	System.out.println("handleChangePricesFromServer");
    	String oldSubsPrice = command.getCommand(GetOldPricesCommand.class).getSubscriptionPrice();
    	String oldOnePrice = command.getCommand(GetOldPricesCommand.class).getOneTimePurchasePrice();
    	Main.getInstance().goToChangePrices(oldSubsPrice, oldOnePrice, "");
    }
    
    public void handleSendNewPrices(String newOnePrice, String newSubsPrice) throws IOException{
    	commandRequest = true;
        System.out.println("handleSendNewPrices");
        Command command = new Command(new SendNewPricesCommand(newOnePrice, newSubsPrice), CommandType.SendNewPricesCommand);
        sendToServer(command);
        waitForServerResponse();
        handleSendNewPricesFromServer();
    }
    
    public void handleSendNewPricesFromServer() throws IOException{
    	System.out.println("handleSendNewPricesFromServer");
    	Main.getInstance().goToGCMManagerServices();
    }
    
    public void handleGetPrices() throws IOException{
    	commandRequest = true;
        System.out.println("handleGetPrices");
        Command command = new Command(new GetPricesCommand(), CommandType.GetPricesCommand);
        sendToServer(command);
        waitForServerResponse();
        handleGetPricesFromServer();
    }
    
    public void handleGetPricesFromServer() throws IOException{
    	System.out.println("handleSendNewPricesFromServer");
    	String oldSubsPrice = command.getCommand(GetPricesCommand.class).getOldSubs();
    	String oldOnePrice = command.getCommand(GetPricesCommand.class).getOldOne();
    	String newSubsPrice = command.getCommand(GetPricesCommand.class).getNewSubs();
    	String newOnePrice = command.getCommand(GetPricesCommand.class).getNewOne();
    	
    	Main.getInstance().goToPricesView(oldSubsPrice, oldOnePrice, newSubsPrice, newOnePrice);
    }
    
    public void handleUpdatePricesAfterAccept(String newSubsPrice, String newOnePrice) throws IOException{
    	commandRequest = true;
        System.out.println("handleUpdatePricesAfterAccept");
        Command command = new Command(new UpdatePricesAfterAcceptCommand(newSubsPrice, newOnePrice), CommandType.UpdatePricesAfterAcceptCommand);
        sendToServer(command);
        waitForServerResponse();
        handleUpdatePricesAfterAcceptFromServer();
    }
    
    public void handleUpdatePricesAfterAcceptFromServer() throws IOException{
    	System.out.println("handleUpdatePricesAfterAcceptFromServer");
    	Main.getInstance().goToCompanyManagerServices();
    }
    
    public void handleUpdatePricesAfterDecline() throws IOException{
    	commandRequest = true;
        System.out.println("handleUpdatePricesAfterAccept");
        Command command = new Command(new UpdatePricesAfterDeclineCommand(), CommandType.UpdatePricesAfterDeclineCommand);
        sendToServer(command);
        waitForServerResponse();
        handleUpdatePricesAfterDeclineFromServer();
    }
    
    public void handleUpdatePricesAfterDeclineFromServer() throws IOException{
    	System.out.println("handleUpdatePricesAfterDeclineFromServer");
    	Main.getInstance().goToCompanyManagerServices();
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
