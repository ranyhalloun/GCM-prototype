package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import Entities.Attraction;
import Entities.AttractionTimePair;
import Entities.City;
import Entities.Map;
import Entities.SearchMapResult;
import Entities.StringIntPair;
import Entities.Tour;
import application.customer.Customer;
import javafx.collections.ObservableList;


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
    public boolean insertNewMap(int id, String cityName, String description, String imagePath) throws SQLException {
    	if(!cityExist(cityName))
            insertNewCity(cityName, 1);
        String sql = "INSERT INTO GCMMaps (cityName, id, description, imagePath) VALUES ('"
                + cityName + "', " + "'" + id + "', " + "'" + description + "', " + "'" + imagePath + "')";
        stmt.executeUpdate(sql);
        return true;
    }

    public void insertNewCity(String name, int version) throws SQLException {
        String sql = "INSERT INTO GCMCities (name, version) VALUES ('" + name + "', " + "'" + version + "')";
        stmt.executeUpdate(sql);
    }
    
    public boolean requestApproval(String cityName) throws SQLException {
        if(!cityExist(cityName) || cityWaiting(cityName)) {
            return false;
        }
        String sql = "INSERT INTO CitiesQueue (name) VALUES ('" + cityName + "')";
        stmt.executeUpdate(sql);
        return true;
    }
    
    public boolean cityExist(String cityName) throws SQLException {
        String sql = "SELECT name FROM GCMCities WHERE name = '" + cityName + "'";
        ResultSet rs = stmt.executeQuery(sql);

        return rs.next();
    }
    
    public boolean cityWaiting(String cityName) throws SQLException {
        String sql = "SELECT name FROM CitiesQueue WHERE name = '" + cityName + "'";
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

        boolean byCityName = !cityName.isEmpty();
        boolean byAttraction = !attractionName.isEmpty();
        boolean byDescription = !description.isEmpty();
        
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
        String sql = "SELECT name FROM CitiesQueue";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())
            cities.add(rs.getString(1));
        return cities;
    }

    private void addAdditionalInfoOfCity(SearchMapResult searchMapResult, String cityName) {
        City cityInfo = searchMapResult.getCity();
        int numOfAttractions = getNumOfAttractionsOfCity(cityName);
        int numOfMaps = getNumOfMaps(cityName);
        ArrayList<Tour> tours = getToursOfCity(cityName);
        int numOfTours = tours.size();
        String cityDescription = getCityDescription(cityName);

        cityInfo.setNumOfAttractions(numOfAttractions);
        cityInfo.setNumOfTours(numOfTours);
        cityInfo.setNumOfMaps(numOfMaps);
        cityInfo.setTours(tours);
        cityInfo.setCityName(cityName);
        cityInfo.setDescription(cityDescription);

        System.out.printf("numOfAttractionsInCity: %d, numOfToursInCity: %d, numOfMapsInCity: %d, cityDescription: %s%n", numOfAttractions, numOfTours, 
                numOfMaps, cityDescription);
        System.out.println("Tours:");
        printTours(tours);
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

    private void printTours(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            System.out.printf("tour id: %d, tour description: %s, attractions:%n",tour.getId(), tour.getDescription());
            printTourAttractions(tour.getAttractionsTimePair());
        }
    }

    private void printTourAttractions(ArrayList<AttractionTimePair> attractionsTimePair) {
        for (AttractionTimePair attr : attractionsTimePair) {
            attr.getAttraction().print();
            System.out.println("Time: " + Integer.toString(attr.getTime()));
        }
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
                    Attraction attraction = new Attraction(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), cityName);
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
        String sql = "SELECT DISTINCT COUNT(attractionID) FROM AttractionsMaps WHERE cityName = '" + cityName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                numOfAttractions = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfAttractions;
    }
  
    private Attraction getAttractionInfo(String attractionName) throws SQLException {
    	String sql = "SELECT id, category, description, isAccessible, cityName FROM Attractions WHERE name = '" + attractionName + "'";
    	
    	ResultSet rs = stmt.executeQuery(sql);
    	rs.next();
    	return (new Attraction(rs.getString(1), attractionName, rs.getString(2), rs.getString(3), Boolean.parseBoolean(rs.getString(4)), rs.getString(5)));
    }

    private String getAttractionDescription(String attractionName) {
        String description = "";
        String sql = "SELECT description FROM Attractions WHERE name = '" + attractionName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                description = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }

    private ArrayList<String> getCitiesNameOfAttraction(String attractionName) {
        ArrayList<String> citiesName = new ArrayList<String>();
        String sql = "SELECT DISTINCT cityName FROM Attractions WHERE Attractions.name = '" + attractionName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
                citiesName.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citiesName;
    }

    private int getNumOfMapsIncludeAttraction(ArrayList<Map> maps, String attractionName) {
        int numOfMaps = 0;
        for (Map map : maps) {
            
        }
        return numOfMaps;
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
        String sql = "SELECT Maps.id FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions "
                + "ON Attractions.name = AttractionsMaps.attractionName  WHERE Maps.cityName = '" + cityName + "' AND Attractions.name = '"
                + attraction + "' AND Maps.description LIKE '%" + description + "%'";
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
        String sql = "SELECT Maps.id FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '"
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
        String sql = "SELECT DISTINCT Maps.id FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.id = AttractionsMaps.attractionID  WHERE Attractions.name = '"
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
        String sql = "SELECT mapID FROM AttractionsMaps INNER JOIN Attractions ON Attractions.id = AttractionsMaps.attractionID WHERE Attractions.name = '" + attractionName + "'";
        
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
        
        String sql = "SELECT AttractionsMaps.attractionID, AttractionsMaps.coordinate, "
                + "Attractions.name, Attractions.category, Attractions.description, "
                + "Attractions.isAccessible, Maps.cityName FROM AttractionsMaps INNER JOIN Maps "
                + "ON AttractionsMaps.mapID = Maps.id INNER JOIN Attractions "
                + "ON Attractions.id = AttractionsMaps.attractionID WHERE mapID = '" + mapID + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String attractionID = rs.getString(1);
                String location = rs.getString(2);
                String attractionName = rs.getString(3);
                String category = rs.getString(4);
                String description = rs.getString(5);
                boolean accessible = rs.getBoolean(6);
                String cityName = rs.getString(7);
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
    
    public void removeAttractionFromTour(String attractionID, int tourID) throws SQLException {
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
    	String sql = "SELECT DISTINCT Attractions.id, Attractions.name, Attractions.category, Attractions.description, "
    	        + "Attractions.isAccessible FROM AttractionsMaps INNER JOIN Maps ON "
    	        + "AttractionsMaps.mapID = Maps.id INNER JOIN Attractions ON "
    	        + "Attractions.id = AttractionsMaps.attractionID WHERE Maps.cityName = '" + cityName + "'";
    	ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
			    String attractionID = rs.getString(1);
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
	            String attractionID = rs.getString(1);
	            for (Attraction attr : attractions) {
	            	if (attractionID.equals(attr.getId())) {
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
    
   public void addAttractionToTour(String attractionID, int tourID, int time, String cityName) throws SQLException {
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
                Attraction attraction = new Attraction(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), cityName);
                attractionsTimePair.add(new AttractionTimePair(attraction, time));
            }
            tour.setAttractionsTimePair(attractionsTimePair);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tour;
    }
    
    
}
