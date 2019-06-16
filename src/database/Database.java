package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.mail.MessagingException;

import commands.SendRenewalEmailCommand;
import Entities.Attraction;
import Entities.AttractionTimePair;
import Entities.City;
import Entities.DownloadDetails;
import Entities.Map;
import Entities.PurchaseDetails;
import Entities.Report;
import Entities.Tour;
import Entities.ViewDetails;
import Entities.updateCityRequest;
import Entities.Coordinates;
import application.arrayOfStrings;
import application.customer.Customer;
import commands.Command;
import commands.CommandType;
import commands.SendEditedMapsDecisionCommand;
import commands.SendNewEditedMapsEmailCommand;
import commands.SendNewPricesDecisionEmailCommand;
import commands.SendNewPricesRequestEmailCommand;
import commands.SendNewVersionCommand;
import server.Email;


public class Database {
    
    static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static private final String DB = "ejI4Uj5gY7";
    static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
    static private final String USER = "ejI4Uj5gY7";
    static private final String PASS = "R0soiZY0p3";
    
    Connection conn;
    Statement stmt;
    
    public Database()
    {
        conn = null;
        stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void registerNewCustomer(String firstname, String lastname, String username, String password, String email, String phone) throws SQLException
    {
    	String sql = "INSERT INTO Users (username, password, firstName, lastName, emailAddress, phoneNumber) VALUES ('"
    	+ username + "', " + "'" + password + "', " + "'" + firstname + "', " + "'" + lastname + "', " + "'" + email + "', " + "'" + phone + "')";
    	stmt.executeUpdate(sql);
    	
    	sql = "INSERT INTO Customers (customerUsername) VALUES ('" + username + "')";
    	stmt.executeUpdate(sql);
    }
    
    //check if input error!!
    public boolean insertNewMap(Map map) throws SQLException {
        String cityName = map.getCityName();
        String description = map.getDescription();
        String imagePath = map.getImagePath();
        int mapID = map.getMapID();
        if(mapIDExist(mapID)) {
            return false;
        }
        String sql = "INSERT INTO GCMMaps (cityName, id, description, imagePath) VALUES ('"
                + cityName + "', " + "'" + mapID + "', " + "'" + description + "', " + "'" + imagePath + "')";
        stmt.executeUpdate(sql);
        return true;
    }

    public boolean insertNewCity(String name, String description) throws SQLException {
        if(cityExist(name))
            return false;
        String sql = "INSERT INTO GCMCities (name, description ,version) VALUES ('"
                + name + "', " + "'" + description + "', " + "'" + 1 + "')";
        stmt.executeUpdate(sql);
        return true;
    }
    
    public int requestApproval(String cityName) throws SQLException {
        if(!cityExist(cityName)) {
            return -1;
        }
        if(cityWaiting(cityName))
            return 0;
        String sql = "INSERT INTO CitiesQueue (name) VALUES ('" + cityName + "')";
        stmt.executeUpdate(sql);
        
        sql = "SELECT emailAddress FROM  Users WHERE role = 'GCMManager'";
    	ResultSet rs = stmt.executeQuery(sql);
    	
    	while(rs.next()) {
    		Command command = new Command(new SendNewEditedMapsEmailCommand(rs.getString(1)), CommandType.SendNewEditedMapsEmailCommand);
    		try {
				Email.sendEmail(command);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    	}
        
        return 1;
    }
    
    public boolean cityExist(String cityName) throws SQLException {
        String sql = "SELECT name FROM GCMCities WHERE name = '" + cityName + "'";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
    }
    
    public boolean mapIDExist(int mapID) throws SQLException {
        String sql = "SELECT id FROM GCMMaps WHERE id = '" + mapID + "'";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
    }
    
    public boolean cityWaiting(String cityName) throws SQLException {
        String sql = "SELECT name FROM CitiesQueue WHERE name = '" + cityName + "' AND answer = -1";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
    }
    
    public boolean authenticate(String username, String password) throws SQLException
    {
        boolean success = false;
        String sql = "SELECT username, password FROM Users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
            success = password.equals(rs.getString("password"));
        return success;
    }
    
    public String getRole(String username) throws SQLException
    {
        String role = "";
        String sql = "SELECT role FROM Users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
            role = rs.getString("role");
        return role;
    }
    
    public boolean usernameExist(String username) throws SQLException{
        
        String sql = "SELECT username FROM Users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
            
        return rs.next();
    }
    
    public City searchCity(String cityName) {
        City city = new City();
        int numOfAttractions = getNumOfAttractionsOfCity(cityName);
        int numOfMaps = getNumOfMaps(cityName);
        int numOfTours = getToursOfCity(cityName).size();
        String cityDescription = getCityDescription(cityName);

        city.setNumOfAttractions(numOfAttractions);
        city.setNumOfTours(numOfTours);
        city.setNumOfMaps(numOfMaps);
        city.setCityName(cityName);
        city.setDescription(cityDescription);

        System.out.printf("numOfAttractionsInCity: %d, numOfToursInCity: %d, numOfMapsInCity: %d, cityDescription: %s%n", numOfAttractions, numOfTours, 
                numOfMaps, cityDescription);
        return city;
    }
    
    public ArrayList<Map> searchMaps(String attractionName, String cityName, String description) throws SQLException {
        ArrayList<Map> maps = new ArrayList<Map>();

        System.out.println("Before Find Maps.");
        maps = findMaps(cityName, attractionName, description);
        
        printMaps(maps);
        
        return maps;
    }

    private void printMaps(ArrayList<Map> maps) {
        for (Map map : maps) {
            map.print();
        }
    }

    public ArrayList<String> getCitiesQueue() throws SQLException {
        ArrayList<String> cities = new ArrayList<String>();
        String sql = "SELECT name FROM CitiesQueue WHERE answer = -1";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())
            cities.add(rs.getString(1));
        return cities;
    }
    
    private String getCityDescription(String cityName) {
        String description = "";
        String sql = "SELECT description FROM Cities WHERE name = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                description = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }

    public ArrayList<Tour> getToursOfCity(String cityName) {
        ArrayList<Tour> tours = new ArrayList<Tour>();
        String sql = "SELECT id, description FROM Tours WHERE cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int tourID = rs.getInt(1);
                String description = rs.getString(2);
                Tour tour = new Tour(tourID, description, cityName);
                tours.add(tour);
            }
            for (Tour tour : tours) {
                sql = "SELECT ToursAttractions.time, ToursAttractions.attractionID, Attractions.name, Attractions.category, "
                        + "Attractions.description, Attractions.isAccessible FROM ToursAttractions INNER JOIN Attractions ON "
                        + "ToursAttractions.attractionID = Attractions.id WHERE ToursAttractions.tourID = '" + Integer.toString(tour.getId()) + "'";
                rs = stmt.executeQuery(sql);
                ArrayList<AttractionTimePair> attractionsTimePair = new ArrayList<AttractionTimePair>();
                while (rs.next()) {
                    int time = rs.getInt(1);
                    Attraction attraction = new Attraction(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), cityName);
                    attractionsTimePair.add(new AttractionTimePair(attraction, time));
                }
                tour.setAttractionsTimePair(attractionsTimePair);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    private int getNumOfMaps(String cityName) {
        int numOfMaps = -1;
        String sql = "SELECT COUNT(id) FROM Maps WHERE cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                numOfMaps = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfMaps;
    }

    private int getNumOfAttractionsOfCity(String cityName){
        int numOfAttractions = -1;
        String sql = "SELECT DISTINCT COUNT(id) FROM Attractions WHERE cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                numOfAttractions = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfAttractions;
    }

    private ArrayList<Map> findMaps(String cityName, String attractionName, String description) {
        ArrayList<Map> maps = new ArrayList<Map>();
        boolean byCityName = !cityName.isEmpty();
        boolean byAttraction = !attractionName.isEmpty();
        boolean byDescription = !description.isEmpty();
        
        //Only By City
        if (byCityName && !byAttraction && !byDescription) {
            System.out.println("Search By CityName");
            findMapsByCity(maps, cityName);
        }
        
        //Only By Attraction
        if (!byCityName && byAttraction && !byDescription) {
            System.out.println("Search By Attraction");
            findMapsByAttraction(maps, attractionName);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        //Only By City & Attraction
        if (byCityName && byAttraction && !byDescription) {
            System.out.println("Search By CityName & Attraction");
            findMapsByCityAttraction(maps, cityName, attractionName);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        //Only By City & Description
        if (byCityName && !byAttraction && byDescription) {
            System.out.println("Search By CityName & Description");
            findMapsByCityDescription(maps, cityName, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        //Only By Attraction & Description
        if (!byCityName && byAttraction && byDescription) {
            System.out.println("Search By Attrction & Description");
            findMapsByAttractionDescription(maps, attractionName, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        //Only By Description
        if (!byCityName && !byAttraction && byDescription) {
            System.out.println("Search By Description");
            findMapsByDescription(maps, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        //Search By City & Attraction & Description
        if (byCityName && byAttraction && byDescription) {
            System.out.println("Search By Description");
            findMapsByCityAttractionDescription(maps, cityName, attractionName, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
        }
        return maps;
    }
    
    private void findMapsByCityAttractionDescription(ArrayList<Map> maps, String cityName, String attraction,
            String description) {
        String sql = "SELECT Maps.id FROM Maps INNER JOIN Attractions ON Maps.id = Attractions.mapID WHERE Maps.cityName = '" 
                + cityName + "' AND Attractions.name = '" + attraction + "' AND Maps.description LIKE '%" + description + "%'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mapID = rs.getInt(1);
                Map map = new Map(mapID);
                maps.add(map);
            }
            for (Map map : maps) {
                Map res = findMapByMapID(map.getMapID());
                map.setDescription(res.getDescription());
                map.setCityName(res.getCityName());
                map.setImagePath(res.getImagePath());
                map.setAttractions(res.getAttractions());
                map.setVersion(res.getVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findMapsByAttractionDescription(ArrayList<Map> maps, String attraction, String description) {
        String sql = "SELECT Maps.id FROM Maps INNER JOIN Attractions ON Maps.id = Attractions.mapID WHERE Attractions.name = '"
                + attraction + "' AND Attractions.description LIKE '%" + description + "%'";
          try {
              ResultSet rs = stmt.executeQuery(sql);
              while (rs.next()) {
                  int mapID = rs.getInt(1);
                  Map map = new Map(mapID);
                  maps.add(map);
              }
              for (Map map : maps) {
                  Map res = findMapByMapID(map.getMapID());
                  map.setDescription(res.getDescription());
                  map.setCityName(res.getCityName());
                  map.setImagePath(res.getImagePath());
                  map.setAttractions(res.getAttractions());
                  map.setVersion(res.getVersion());
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
    }

    private void findMapsByCityDescription(ArrayList<Map> maps, String cityName, String description) {
        String sql = "SELECT id FROM Maps WHERE cityName = '" + cityName + "' AND description LIKE '%" + description + "%'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mapID = rs.getInt(1);
                Map map = new Map(mapID);
                maps.add(map);
            }
            for (Map map : maps) {
                Map res = findMapByMapID(map.getMapID());
                map.setDescription(res.getDescription());
                map.setCityName(res.getCityName());
                map.setImagePath(res.getImagePath());
                map.setAttractions(res.getAttractions());
                map.setVersion(res.getVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private void findMapsByCityAttraction(ArrayList<Map> maps, String cityName, String attraction) {
        String sql = "SELECT DISTINCT Maps.id FROM Maps INNER JOIN Attractions ON Maps.id = Attractions.mapID WHERE Attractions.name = '"
              + attraction + "' AND Maps.cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mapID = rs.getInt(1);
                Map map = new Map(mapID);
                maps.add(map);
            }
            for (Map map : maps) {
                Map res = findMapByMapID(map.getMapID());
                map.setDescription(res.getDescription());
                map.setCityName(res.getCityName());
                map.setImagePath(res.getImagePath());
                map.setAttractions(res.getAttractions());
                map.setVersion(res.getVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO: UNFINISHED! Search by descriptions of City not map -- SLOW
    private void findMapsByDescription(ArrayList<Map> maps, String description) {
        maps.clear();
        LinkedHashSet<Map> noDupMaps = new LinkedHashSet<Map>();
        ArrayList<Map> cityDescription = new ArrayList<Map>();
        ArrayList<Map> attractionDescription = new ArrayList<Map>();
        ArrayList<String> relatedCities = new ArrayList<String>();
        ArrayList<String> relatedAttractions = new ArrayList<String>();
        String sql = "SELECT cityName FROM Maps WHERE description LIKE '%" + description + "%'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String cityName = rs.getString(1);
                relatedCities.add(cityName);
            }
            for (String cityName : relatedCities) {
                findMapsByCity(cityDescription, cityName);
            }
            sql = "SELECT name FROM Attractions WHERE description LIKE '%" + description + "%'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String attractionName = rs.getString(1);
                relatedAttractions.add(attractionName);
            }
            for (String attraction : relatedAttractions) {
                findMapsByAttraction(attractionDescription, attraction);
            }
            noDupMaps.addAll(cityDescription);
            noDupMaps.addAll(attractionDescription);
            
            // noDupMaps now contains all the maps without duplications
            
            maps.addAll(noDupMaps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findMapsByAttraction(ArrayList<Map> maps, String attractionName) {
        String sql = "SELECT mapID FROM Attractions WHERE Attractions.name = '" + attractionName + "'";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mapID = rs.getInt(1);
                Map map = new Map(mapID);
                maps.add(map);
            }
            for (Map map : maps) {
                Map res = findMapByMapID(map.getMapID());
                map.setDescription(res.getDescription());
                map.setCityName(res.getCityName());
                map.setImagePath(res.getImagePath());
                map.setAttractions(res.getAttractions());
                map.setVersion(res.getVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findMapsByCity(ArrayList<Map> maps, String cityName) {
        String sql = "SELECT id FROM Maps WHERE cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mapID = rs.getInt(1);
                Map map = new Map(mapID);
                maps.add(map);
            }
            for (Map map : maps) {
                Map res = findMapByMapID(map.getMapID());
                map.setDescription(res.getDescription());
                map.setCityName(res.getCityName());
                map.setImagePath(res.getImagePath());
                map.setAttractions(res.getAttractions());
                map.setVersion(res.getVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
    
    public Map findMapByMapID(int mapID) {
        String sql = "SELECT cityName, description, imagePath FROM Maps WHERE id = '" + mapID + "'";
        Map map = new Map();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String cityName = rs.getString(1);
                String description = rs.getString(2);
                String imagePath = rs.getString(3);
                ArrayList<Attraction> attractions = getAttractionsOfMap(mapID);
                int version = getCityVersion(cityName);
                map = new Map(mapID, description, cityName, imagePath, attractions, version);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    private int getCityVersion(String cityName) {
        int version = -1;
        
        String sql = "SELECT version FROM Cities WHERE name = '" + cityName + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                version = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return version;
    }

    private ArrayList<Attraction> getAttractionsOfMap(int mapID) {
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        
        String sql = "SELECT id, xCoord, yCoord, name, category, description, isAccessible, cityName FROM Attractions WHERE mapID = '" + mapID + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int attractionID = rs.getInt(1);
                Coordinates location = new Coordinates(rs.getDouble(2), rs.getDouble(3));
                String attractionName = rs.getString(4);
                String category = rs.getString(5);
                String description = rs.getString(6);
                boolean accessible = rs.getBoolean(7);
                String cityName = rs.getString(8);
                Attraction attraction = new Attraction(attractionID, attractionName, category, description, accessible, cityName, location);
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }

    // TODO: fix if(rs.next())
    public Customer getCustomerInfo(Object object) throws SQLException {
        String sql = "SELECT username, password, firstName, lastName, emailAddress, PhoneNumber FROM Users WHERE username = '" + object + "'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
    }
    
    public boolean editCustomerInfo(Customer customer, String oldUsername) throws SQLException {
        if((!(customer.getUsername().equals(oldUsername)))&&usernameExist(customer.getUsername())) {
            return false;
        }
    	String sql = "UPDATE Users SET username = '" + customer.getUsername() + "', password = '" + customer.getPassword() + "', firstName = '" + customer.getFirstName() +
    				"', lastName = '" + customer.getLastName() + "', emailAddress = '" + customer.getEmail() + "', phoneNumber = '" + customer.getPhone() + "' WHERE username = '" + oldUsername + "'";
    	stmt.executeUpdate(sql);
    	return true;
    }
    
    public void removeAttractionFromTour(int attractionID, int tourID) throws SQLException {
    	String sql = "DELETE FROM ToursAttractions WHERE attractionID = '" + attractionID + "' AND tourID = '" + tourID + "'";
    	stmt.executeUpdate(sql);
    }
    
    public void removeTourFromCityTours(int tourID) {
        String sql = "DELETE FROM Tours WHERE id = '" + tourID + "'";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Attraction> getAttractionsOfCity(String cityName, int tourID){
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        String sql = "SELECT DISTINCT id, name, category, description, isAccessible FROM Attractions WHERE cityName = '" + cityName + "'";
        ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
			    int attractionID = rs.getInt(1);
	            String attractionName = rs.getString(2);
	            String category = rs.getString(3);
	            String description = rs.getString(4);
	            boolean isAccessible = rs.getBoolean(5);
	            Attraction attraction = new Attraction(attractionID, attractionName, category, description, isAccessible, cityName);
	            attractions.add(attraction);
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT attractionID FROM ToursAttractions WHERE tourID = '" + tourID + "'";
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
	            int attractionID = rs.getInt(1);
	            for (Attraction attr : attractions) {
                    if (attractionID == attr.getId()) {
	    	            attractions.remove(attr);
	            		break;
	            	}
	            }
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attractions;
    }
    
    public void addAttractionToTour(int attractionID, int tourID, int time, String cityName) throws SQLException {
        System.out.println("ADDING ATTRACTION TO TOUR!!!");
        System.out.println("attractionID: " + attractionID + " tourID: " + tourID + " time: " + time + " cityName: " + cityName);
    	String sql = "INSERT INTO  ToursAttractions (tourID, attractionID, cityName, time) VALUES ('"
    			 + tourID + "', " + "'" + attractionID + "', " + "'" + cityName + "', " + "'" + time + "')";

    	stmt.executeUpdate(sql);
   }
   
    public void addTourToCity(String cityName, String description) {
        // find unique id
        int id = -1;
        String sql = "SELECT MAX(id) FROM Tours";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next())
                id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id++;
        // Insert Tour
        sql = "INSERT INTO  Tours (id, cityName, description) VALUES ('" + id + "', " + "'" + cityName + "', " + "'"
                + description + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tour getTourInfoByID(int tourID) {
        Tour tour = new Tour();
        String sql = "SELECT cityName, description FROM Tours WHERE id = '" + tourID + "'";
        String cityName = "";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                cityName = rs.getString(1);
                String description = rs.getString(2);
                tour = new Tour(tourID, description, cityName);
            }
            sql = "SELECT ToursAttractions.time, ToursAttractions.attractionID, Attractions.name, Attractions.category, "
                    + "Attractions.description, Attractions.isAccessible FROM ToursAttractions INNER JOIN Attractions ON "
                    + "ToursAttractions.attractionID = Attractions.id WHERE ToursAttractions.tourID = '" + tourID + "'";
            rs = stmt.executeQuery(sql);
            ArrayList<AttractionTimePair> attractionsTimePair = new ArrayList<AttractionTimePair>();
            while (rs.next()) {
                int time = rs.getInt(1);
                Attraction attraction = new Attraction(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), cityName);
                attractionsTimePair.add(new AttractionTimePair(attraction, time));
            }
            tour.setAttractionsTimePair(attractionsTimePair);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tour;
    }
    
    public void updateDBAfterDecline(String cityName) throws SQLException {
        int version = getCityVersion(cityName);
        String sql = "DELETE FROM GCMCities WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO GCMCities SELECT * FROM Cities WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO GCMMaps SELECT * FROM Maps WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO GCMAttractions SELECT * FROM Attractions WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO GCMTours SELECT * FROM Tours WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO GCMToursAttractions SELECT * FROM ToursAttractions WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);
        
        sql = "UPDATE CitiesQueue SET answer = 0 , date = '" + LocalDate.now() + "', version = '" + version + "' WHERE name = '" + cityName + "' AND answer = -1";
        stmt.execute(sql);

        sql = "SELECT emailAddress FROM  Users WHERE role = 'GCMWorker'";
    	ResultSet rs = stmt.executeQuery(sql);
    	
    	while(rs.next()) {
    		Command command = new Command(new SendEditedMapsDecisionCommand(0, rs.getString(1), cityName), CommandType.SendEditedMapsDecisionCommand);
    		try {
				Email.sendEmail(command);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    	}
    }

    public void updateDBAfterAccept(String cityName) throws SQLException {
        int newVersion = getCityVersion(cityName) + 1;
        String sql = "DELETE FROM Cities WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO Cities SELECT * FROM GCMCities WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO Maps SELECT * FROM GCMMaps WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO Attractions SELECT * FROM GCMAttractions WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO Tours SELECT * FROM GCMTours WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "INSERT INTO ToursAttractions SELECT * FROM GCMToursAttractions WHERE cityName = '" + cityName + "'";
        stmt.executeUpdate(sql);

        sql = "UPDATE Cities SET version = '" + newVersion + "' WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);
        
        sql = "UPDATE CitiesQueue SET answer = 1 , date = '" + LocalDate.now() + "', version = '" + newVersion + "' WHERE name = '" + cityName + "' AND answer = -1";
        stmt.execute(sql);

        sql = "SELECT emailAddress FROM  Users WHERE role = 'Customer'";
    	ResultSet rs = stmt.executeQuery(sql);
    	
    	while(rs.next()) {
    		Command command = new Command(new SendNewVersionCommand(rs.getString(1), cityName), CommandType.SendNewVersionCommand);
    		try {
				Email.sendEmail(command);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    	}
    	
        sql = "SELECT emailAddress FROM  Users WHERE role = 'GCMWorker'";
    	rs = stmt.executeQuery(sql);
    	
    	while(rs.next()) {
    		Command command = new Command(new SendEditedMapsDecisionCommand(1, rs.getString(1), cityName), CommandType.SendEditedMapsDecisionCommand);
    		try {
				Email.sendEmail(command);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    	}
    }
    
    /*public void updateCitiesQueue(String cityName) throws SQLException {
    	
        String sql = "DELETE FROM CitiesQueue WHERE name = '" + cityName + "'";
        stmt.executeUpdate(sql);
    }*/

    public ArrayList<Map> getNewExternalMaps() throws SQLException {
        ArrayList<Map> maps = new ArrayList<Map>();
        String sql = "SELECT * FROM ExternalMaps LEFT JOIN GCMMaps on  ExternalMaps.id = GCMMaps.id WHERE GCMMaps.id IS NULL";
        ResultSet rs = stmt.executeQuery(sql);
        int mapID;
        String description = "", cityName = "", imagePath = "";
        while(rs.next()){
            cityName = rs.getString(1);
            mapID = rs.getInt(2);
            description = rs.getString(3);
            imagePath = rs.getString(4);

            maps.add(new Map(mapID, description, cityName, imagePath));
        }
        return  maps;
    }

    public ArrayList<String> getCurrentPrices() throws SQLException {
        ArrayList<String> prices = new ArrayList<String>();
        String sql = "SELECT subscriptionPrice, oneTimePurchasePrice FROM Prices";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        prices.add(rs.getString(1));
        prices.add(rs.getString(2));
        return prices;
    }

    public void sendNewPrices(String newOnePrice, String newSubsPrice) throws SQLException {
        String sql = "";
        if(!newOnePrice.isEmpty() && !newSubsPrice.isEmpty())
                sql = "UPDATE Prices SET nextSubscriptionPrice = '" + newSubsPrice + "', nextOneTimePurchasePrice = '" + newOnePrice +  "'";

        else if (!newOnePrice.isEmpty())
            sql = "UPDATE Prices SET nextOneTimePurchasePrice = '" + newOnePrice +  "'";

        else if (!newSubsPrice.isEmpty())
            sql = "UPDATE Prices SET nextSubscriptionPrice = '" + newSubsPrice +  "'";
        stmt.executeUpdate(sql);
        
        sql = "SELECT emailAddress FROM  Users WHERE role = 'CompanyManager'";
    	ResultSet rs = stmt.executeQuery(sql);
    	
    	while(rs.next()) {
    		Command command = new Command(new SendNewPricesRequestEmailCommand(rs.getString(1)), CommandType.SendNewPricesRequestEmailCommand);
    		try {
				Email.sendEmail(command);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    	}
    }

    public ArrayList<String> getPrices(String oldSubsPrice, String oldOnePrice, String newSubsPrice, String newOnePrice) throws SQLException {
        ArrayList<String> prices = new ArrayList<String>();
        String sql = "SELECT * FROM Prices";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        prices.add(rs.getString(1));
        prices.add(rs.getString(2));
        prices.add(rs.getString(3));
        prices.add(rs.getString(4));
        return prices;
    }

    public void updatePricesAfterAccept(String newSubsPrice, String newOnePrice) throws SQLException {
    	String sql = "";
       if(!newSubsPrice.equals("-1") && !newOnePrice.equals("-1"))
    	   sql = "UPDATE Prices SET subscriptionPrice = '" + newSubsPrice + "', oneTimePurchasePrice = '" + newOnePrice + "', nextSubscriptionPrice = '" + -1 + "', nextOneTimePurchasePrice = '" + -1 + "'";
       else if(!newSubsPrice.equals("-1"))
    	   sql = "UPDATE Prices SET subscriptionPrice = '" + newSubsPrice + "', nextSubscriptionPrice = '" + -1 + "'";
       else if(!newOnePrice.equals("-1"))
    	   sql = "UPDATE Prices SET oneTimePurchasePrice = '" + newOnePrice + "', nextOneTimePurchasePrice = '" + -1 + "'";
       stmt.executeUpdate(sql);
       
       sql = "SELECT emailAddress FROM  Users WHERE role = 'GCMManager'";
	   	ResultSet rs = stmt.executeQuery(sql);
	   	
	   	while(rs.next()) {
	   		Command command = new Command(new SendNewPricesDecisionEmailCommand(1, rs.getString(1)), CommandType.SendNewPricesDecisionEmailCommand);
	   		try {
					Email.sendEmail(command);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
	   	}

    }

    public void updatePricesAfterDecline() throws SQLException {
        String sql = "UPDATE Prices SET nextSubscriptionPrice = '" + -1 + "', nextOneTimePurchasePrice = '" + -1 + "'";
        stmt.executeUpdate(sql);
        
        sql = "SELECT emailAddress FROM  Users WHERE role = 'GCMWorker'";
 	   	ResultSet rs = stmt.executeQuery(sql);
 	   	
 	   	while(rs.next()) {
 	   		Command command = new Command(new SendNewPricesDecisionEmailCommand(0, rs.getString(1)), CommandType.SendNewPricesDecisionEmailCommand);
 	   		try {
 					Email.sendEmail(command);
 				} catch (MessagingException e) {
 					e.printStackTrace();
 				}
 	   	}
    }
    
    public arrayOfStrings getCustomersCities() throws SQLException {
    	arrayOfStrings cities = new arrayOfStrings();
    	String sql = "SELECT name From Cities";
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next())
        {
        	cities.getArrayList().add(rs.getString(1));
        }
        
        return cities;
    }
    
    public Report getCityReport(String cityName, LocalDate fromDate, LocalDate toDate) throws SQLException {
    	int mapsCounter = getNumOfMaps(cityName);
    	int oneTimeCounter = getNumOfOneTimeBetweenDates(cityName, fromDate, toDate);
    	int subscriptionsCounter = getNumOfSubscriptionsBetweenDates(cityName, fromDate, toDate);
    	int viewsCounter = getNumOfViewsBetweenDates(cityName, fromDate, toDate);
    	int downloadsCounter = getNumOfDownloadsBetweenDates(cityName, fromDate, toDate);
    	
    	return (new Report(mapsCounter, oneTimeCounter, subscriptionsCounter, viewsCounter, downloadsCounter));
    }
    
    
	public int getNumOfOneTimeBetweenDates(String cityName, LocalDate fromDate, LocalDate toDate) {
        int numOfOneTime = -1;
        String sql = "SELECT COUNT(id) FROM PurchaseStatistics WHERE cityName = '" + cityName + "' AND purchaseType = 0 AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            	numOfOneTime = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfOneTime;
	}
	
	private int getNumOfSubscriptionsBetweenDates(String cityName, LocalDate fromDate, LocalDate toDate) {
        int numOfSubs = -1;
        String sql = "SELECT COUNT(id) FROM PurchaseStatistics WHERE cityName = '" + cityName + "' AND purchaseType = 1 AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            	numOfSubs = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfSubs;
	}
    
    private int getNumOfDownloadsBetweenDates(String cityName, LocalDate fromDate, LocalDate toDate) {
        int numOfDownloads = -1;
        String sql = "SELECT COUNT(id) FROM DownloadStatistics WHERE cityName = '" + cityName + "' AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            	numOfDownloads = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfDownloads;
	}

	private int getNumOfViewsBetweenDates(String cityName, LocalDate fromDate, LocalDate toDate) {
        int numOfView = -1;
        String sql = "SELECT COUNT(id) FROM ViewStatistics WHERE cityName = '" + cityName + "' AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            	numOfView = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfView;
	}
	
	public boolean customerExists(String username) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE customerUsername = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
	}
	
	public ArrayList<PurchaseDetails> getPurchases(String customerUsername) throws SQLException {
		ArrayList<PurchaseDetails> purchases = new ArrayList<PurchaseDetails>();
		String sql = "SELECT cityName, date, purchaseType FROM PurchaseStatistics WHERE customerUsername = '" + customerUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String cityName = "";
        LocalDate date;
        int purchaseType;
        while(rs.next()) {
        	cityName = rs.getString(1);
        	date = LocalDate.parse(rs.getString(2));
        	purchaseType = rs.getInt(3);
        	purchases.add(new PurchaseDetails(cityName, date, purchaseType));
        }
        return purchases;
	}
	
	public ArrayList<ViewDetails> getViews(String customerUsername) throws SQLException {
		ArrayList<ViewDetails> views = new ArrayList<ViewDetails>();
		String sql = "SELECT cityName, date, mapID FROM ViewStatistics WHERE customerUsername = '" + customerUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String cityName = "";
        LocalDate date;
        int mapID;
        while(rs.next()) {
        	cityName = rs.getString(1);
        	date = LocalDate.parse(rs.getString(2));
        	mapID = rs.getInt(3);
        	views.add(new ViewDetails(cityName, date, mapID));
        }
        return views;
	}
	
	public ArrayList<DownloadDetails> getDownloads(String customerUsername) throws SQLException {
		ArrayList<DownloadDetails> downloads = new ArrayList<DownloadDetails>();
		String sql = "SELECT cityName, date FROM DownloadStatistics WHERE customerUsername = '" + customerUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String cityName = "";
        LocalDate date;
        while(rs.next()) {
        	cityName = rs.getString(1);
        	date = LocalDate.parse(rs.getString(2));
        	downloads.add(new DownloadDetails(cityName, date));
        }
        return downloads;
	}
	
	public void updateDBAfterPurchasing(int purchaseType, LocalDate date, String customerUsername, String cityName) throws SQLException{
    	String sql = "INSERT INTO PurchaseStatistics (purchaseType, date, customerUsername, cityName) VALUES ('"
    	+ purchaseType + "', " + "'" + date + "', " + "'" + customerUsername + "', " + "'" + cityName + "')";
    	stmt.executeUpdate(sql);
    	
    	if(purchaseType == 1) {
    		sql = "SELECT expirationDate FROM Subscriptions WHERE customerUsername = '" + customerUsername + "' AND cityName = '" + cityName + "'";
    		ResultSet rs = stmt.executeQuery(sql);
    		if(rs.next())
    		{	
    			LocalDate oldDate = LocalDate.parse(rs.getString(1));
    			sql = "UPDATE Subscriptions SET expirationDate  = '" + oldDate.plusMonths(6) + "'";
    		}
    		else {
    			sql = "INSERT INTO Subscriptions (customerUsername, cityName, expirationDate) VALUES ('" + 
    											  customerUsername + "', '" + cityName + "', '" + date.plusMonths(6) + "')";
    		}
    		stmt.executeUpdate(sql);
    	}
	}
	
	public boolean checkSubscription(String customerUsername, String cityName) throws SQLException{
        String sql = "SELECT * FROM Subscriptions WHERE cityName = '" + cityName + "' AND customerUsername = '" + customerUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
	}
	
	public String getExpirationDate(String customerUsername, String cityName) throws SQLException{
        String sql = "SELECT expirationDate FROM Subscriptions WHERE cityName = '" + cityName + "' AND customerUsername = '" + customerUsername + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next())
        	return rs.getString(1);
        else
        	return "";
	}
	
	public ArrayList<updateCityRequest> getManagerNotif() throws SQLException{
		ArrayList<updateCityRequest> notifis = new ArrayList<updateCityRequest>();
		String sql = "SELECT name, answer, date, version FROM CitiesQueue WHERE date IS NOT NULL";
        ResultSet rs = stmt.executeQuery(sql);
        String name = "";
        String answer = "";
        LocalDate date;
        int version;
        while(rs.next()) {
        	name = rs.getString(1);
        	answer = rs.getInt(2) == 1 ? "Accept" : "Decline";
        	date = LocalDate.parse(rs.getString(3));
        	version = rs.getInt(4);
        	notifis.add(new updateCityRequest(name, answer, date, version));
        }
        
        return notifis;
	}
	
	public ArrayList<updateCityRequest> getNewVersions() throws SQLException{
		ArrayList<updateCityRequest> newVersions = new ArrayList<updateCityRequest>();
		String sql = "SELECT name, answer, date, version FROM CitiesQueue WHERE answer = 1";
        ResultSet rs = stmt.executeQuery(sql);
        String name = "";
        LocalDate date;
        int version;
        while(rs.next()) {
        	name = rs.getString(1);
        	date = LocalDate.parse(rs.getString(3));
        	version = rs.getInt(4);
        	newVersions.add(new updateCityRequest(name, date, version));
        }
        return newVersions;
	}
	
	public void removeExpiredSubscriptions() throws SQLException{
		String sql = "DELETE FROM Subscriptions WHERE expirationDate <= '" + LocalDate.now() + "'";
		stmt.executeUpdate(sql);
	}
	
	public void addNewAttractionToMap(int mapID, Attraction attraction) throws SQLException {
        // convert bool to int
        int isAccessibleInt = attraction.getIsAccessible() ? 1 : 0;

        // Create new Attraction  ID
        int newAttractionID = -1;
        String sql = "SELECT MAX(id) FROM Attractions";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next())
                newAttractionID = rs.getInt(1);
            else {
                newAttractionID = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        newAttractionID++;

        sql = "INSERT INTO Attractions (id, name, cityName, mapID, category, description, isAccessible, xCoord, yCoord) VALUES ('"
                + newAttractionID + "', " + "'" + attraction.getName() + "', " + "'" + attraction.getCityName() + "', " 
                + "'" + mapID + "', " + "'" + attraction.getCategory() + "', " + "'" + attraction.getDescription() + "', " + "'" 
                + isAccessibleInt + "', " + "'" + attraction.getLocation().getX_cord() + "', " + "'" + attraction.getLocation().getY_cord() + "')";
        stmt.executeUpdate(sql);
    }

    public void editAttractionInMap(int mapID, Attraction attraction) throws SQLException {
        // Convert bool to int 
        int isAccessibleInt = attraction.getIsAccessible() ? 1 : 0;

        System.out.println("Updating inside database Attraction:");
        attraction.print();

        String sql = "UPDATE Attractions SET id = '" + attraction.getId() + "', name = '" + attraction.getName() + "', cityName = '" + 
                attraction.getCityName() + "', mapID = '" + mapID + "', category = '" + attraction.getCategory() + "', description = '" + 
                attraction.getDescription() + "', isAccessible = '" + isAccessibleInt + "', xCoord = '" + attraction.getLocation().getX_cord() + 
                "', yCoord = '" + attraction.getLocation().getY_cord() + "' WHERE id = '" + attraction.getId() + "'";;

        stmt.executeUpdate(sql);
    }

    public void removeAttractionFromMap(int mapID, int attractionID) throws SQLException {
        String sql = "DELETE FROM Attractions WHERE id = '" + attractionID + "' AND mapID = '" + mapID + "'";
        stmt.executeUpdate(sql);
    }

    public void incrementNumViewOfMap(int mapID, String cityName, Object info) {
        LocalDate date = LocalDate.now();
        String sql = "INSERT INTO ViewStatistics (cityName, date, customerUsername, mapID) VALUES ('"
                + cityName + "', " + "'" + date + "', " + "'" + info.toString() + "', " + "'" + mapID + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incrementNumDownloadsOfMap(int mapID, String cityName, Object info) {
        LocalDate date = LocalDate.now();
        String sql = "INSERT INTO DownloadStatistics (mapID, cityName, customerUsername, date) VALUES ('"
                + mapID + "', " + "'" + cityName + "', " + "'" + info.toString() + "', " + "'" + date + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void sendRenewalReminder() throws SQLException, MessagingException {
    	String sql = "SELECT Users.emailAddress, Subscriptions.cityName FROM  Subscriptions INNER JOIN Users ON Users.username"
    			+ " = Subscriptions.customerUsername WHERE Subscriptions.expirationDate = '" + LocalDate.now().plusDays(3) + "'";
    	ResultSet rs = stmt.executeQuery(sql);
    	while(rs.next()) {
	        Command command = new Command(new SendRenewalEmailCommand(rs.getString(1), rs.getString(2)), CommandType.SendRenewalEmailCommand);
    		Email.sendEmail(command);
    	}
    	
    }

}
