package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import Entities.Attraction;
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
    
    // Should we have return value?
    // What to do in case of error?
    public void registerNewCustomer(String firstname, String lastname, String username, String password, String email, String phone) throws SQLException
    {
    	String sql = "INSERT INTO Users (username, password, firstName, lastName, emailAddress, phoneNumber) VALUES ('"
    	+ username + "', " + "'" + password + "', " + "'" + firstname + "', " + "'" + lastname + "', " + "'" + email + "', " + "'" + phone + "')";
    	stmt.executeUpdate(sql);
    	
    	sql = "INSERT INTO Customers (customerUsername) VALUES ('"
    	+ username + "')";
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
        String sql = "INSERT INTO GCMCities (name, version) VALUES ('"
                + name + "', " + "'" + version + "')";
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
    
    public SearchMapResult searchMap(String attraction, String cityName, String description) throws SQLException {
        System.out.println("1");
        SearchMapResult searchMapResult = new SearchMapResult();
        System.out.println("2");
        ArrayList<Map> maps = new ArrayList<Map>();
        System.out.println("3");
        if (!cityName.isEmpty()) {
            System.out.println("4");
            searchMapResult.setSearchByCity(true);
            searchMapByCityName(searchMapResult, cityName);
        }
        System.out.println("8");
        if (!attraction.isEmpty()) {
            System.out.println("9");
            searchMapResult.setSearchByAttraction(true);
            searchMapByAttraction(searchMapResult, attraction);
            System.out.println("10");

            // Search by Attraction only
            if (cityName.isEmpty() && description.isEmpty()) {
                System.out.println("12");
            }
        }
        
        System.out.println("13");
        findMaps(maps, cityName, attraction, description);
        printMaps(maps);
        System.out.println("14");
        searchMapResult.setMaps(maps);
        System.out.println("15");
        
        return searchMapResult;
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

    private void searchMapByCityName(SearchMapResult searchMapResult, String cityName) {        
        int numOfAttractions = getNumOfAttractions(cityName);
        int numOfMaps = getNumOfMaps(cityName);
        ArrayList<Tour> tours = getTours(cityName);
        int numOfTours = tours.size();
        
        searchMapResult.getCity().setNumOfAttractions(numOfAttractions);
        searchMapResult.getCity().setNumOfTours(numOfTours);
        searchMapResult.getCity().setNumOfMaps(numOfMaps);
        searchMapResult.getCity().setTours(tours);
        searchMapResult.getCity().setCityName(cityName);
        
        System.out.printf("numOfAttractions: %d - numOfTours: %d - numOfMaps: %d%n", numOfAttractions, numOfTours, numOfMaps);
        System.out.println("Tours:");
        printTours(tours);
    }
    
    private void printTours(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            System.out.printf("tour id: %d, tour description: %s, attractions:%n",tour.getId(), tour.getDescription());
            printTourAttractions(tour.getAttractionsName());
        }
    }

    private void printTourAttractions(ArrayList<StringIntPair> attractionsName) {
        for (StringIntPair attr : attractionsName) {
            System.out.printf("attraction name: %s, time: %d%n", attr.getString_field(), attr.getInt_field());
        }
    }

    public ArrayList<Tour> getTours(String cityName) {
        ArrayList<Tour> tours = new ArrayList<Tour>();
        
        String sql = "SELECT DISTINCT Tours.description, ToursCity.tourID FROM "
                + "Tours INNER JOIN ToursCity ON Tours.id = ToursCity.tourID WHERE Tours.cityName = '" + cityName + "'";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String description = rs.getString(1);
                int tourID = rs.getInt(2);
                Tour tour = new Tour(tourID, description);
                tours.add(tour);
            }
            
            for (Tour tour : tours) {
                sql = "SELECT Attractions.name, ToursCity.time FROM ToursCity INNER JOIN Attractions ON ToursCity.attractionID = Attractions.id WHERE ToursCity.tourID = '" + Integer.toString(tour.getId()) + "'";
                rs = stmt.executeQuery(sql);
                ArrayList<StringIntPair> attractions = new ArrayList<StringIntPair>();
                while (rs.next()) {
                    String attractionName = rs.getString(1);
                    int time = rs.getInt(2);
                    attractions.add(new StringIntPair(attractionName, time));
                }
                tour.setAttractionsName(attractions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    private int getNumOfMaps(String cityName) {
        int numOfMaps = -1;

        String sql = "SELECT COUNT(cityName) FROM Maps WHERE cityName = '" + cityName + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next());
                numOfMaps = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numOfMaps;
    }

    private int getNumOfAttractions(String cityName){
        int numOfAttractions = -1;
        String sql = "SELECT COUNT(cityName) FROM Attractions WHERE cityName = '" + cityName + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next());
                numOfAttractions = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numOfAttractions;
    }

    // We Assume that there is only 1 city that has this attraction...
    private void searchMapByAttraction(SearchMapResult searchMapResult, String attraction) throws SQLException {
        System.out.println("11");
        String cityName = getCityNameOfAttraction(attraction);
        System.out.println("22");
        int numOfMapsIncludeAttraction = getNumOfMapsIncludeAttraction(attraction);
        System.out.println("33");
        String attractionDescription = getAttractionDescription(attraction);
        System.out.println("44");
        searchMapResult.setCityNameOfAttraction(cityName);
        System.out.println("55");
        searchMapResult.setNumOfMapsIncludeAttraction(numOfMapsIncludeAttraction);
        System.out.println("66");
        searchMapResult.setAttractionDescription(attractionDescription);
        
        searchMapResult.setAttraction(getAttractionInfo(attraction));
        
        
        
        System.out.printf("cityName: %s, numOfMapsIncludeAttraction: %d, attractionDescription: %s%n",
                cityName, numOfMapsIncludeAttraction, attractionDescription);
    }
    
    private Attraction getAttractionInfo(String attraction) throws SQLException {
    	String sql = "SELECT category, description, isAccessible FROM Attractions WHERE name = '" + attraction + "'";
    	
    	ResultSet rs = stmt.executeQuery(sql);
    	rs.next();
    	return (new Attraction(attraction, rs.getString(1), rs.getString(2), Boolean.parseBoolean(rs.getString(3))));
    }

    private String getAttractionDescription(String attraction) {
        String description = "";
        
        String sql = "SELECT description FROM Attractions WHERE name = '" + attraction + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                description = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return description;
    }

    private String getCityNameOfAttraction(String attraction) {
        String cityName = "";
        String sql = "SELECT cityName FROM Attractions WHERE name = '" + attraction + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                cityName = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityName;
    }

    private int getNumOfMapsIncludeAttraction(String attraction) {
        int numOfMaps = -1;

        String sql = "SELECT COUNT(mapID) FROM AttractionsMaps WHERE attractionName = '" + attraction + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                numOfMaps = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numOfMaps;
    }

    private void findMaps(ArrayList<Map> maps, String cityName, String attraction, String description) {
        boolean byCityName = !cityName.isEmpty();
        boolean byAttraction = !attraction.isEmpty();
        boolean byDescription = !description.isEmpty();
        
        //Only By City
        if (byCityName && !byAttraction && !byDescription) {
            System.out.println("Search By CityName");
            findMapsByCity(maps, cityName);
            return;
        }
        //Only By Attraction
        if (!byCityName && byAttraction && !byDescription) {
            System.out.println("Search By Attraction");
            findMapsByAttraction(maps, attraction);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
        //Only By City & Attraction
        if (byCityName && byAttraction && !byDescription) {
            System.out.println("Search By CityName & Attraction");
            findMapsByCityAttraction(maps, cityName, attraction);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
        //Only By City & Description
        if (byCityName && !byAttraction && byDescription) {
            System.out.println("Search By CityName & Description");
            findMapsByCityDescription(maps, cityName, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
        //Only By Attraction & Description
        if (!byCityName && byAttraction && byDescription) {
            System.out.println("Search By Attrction & Description");
            findMapsByAttractionDescription(maps, attraction, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
        //Only By Description
        if (!byCityName && !byAttraction && byDescription) {
            System.out.println("Search By Description");
            findMapsByDescription(maps, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
        //Search By City & Attraction & Description
        if (byCityName && byAttraction && byDescription) {
            System.out.println("Search By Description");
            findMapsByCityAttractionDescription(maps, cityName, attraction, description);
            System.out.println("Num of maps: " + Integer.toString(maps.size()));
            return;
        }
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
        String sql = "SELECT Maps.id FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '"
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

    private void findMapsByAttraction(ArrayList<Map> maps, String attraction) {
        String sql = "SELECT mapID FROM AttractionsMaps WHERE attractionName = '" + attraction + "'";
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
    
    private Map findMapByMapID(int mapID) {
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
        
        String sql = "SELECT attractionName, coordinate FROM AttractionsMaps WHERE mapID = '" + mapID + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String attractionName = rs.getString(1);
                String location = rs.getString(2);
                Attraction attraction = new Attraction(attractionName, location);
                attractions.add(attraction);
            }
            for (Attraction attraction : attractions) {
                sql = "SELECT category, description, isAccessible, cityName FROM Attractions WHERE name = '" + attraction.getName() + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    String category = rs.getString(1);
                    String description = rs.getString(2);
                    boolean isAccessible = rs.getBoolean(3); //TODO: Check if it can convert tinyInt to boolean
                    String cityName = rs.getString(4);
                    
                    attraction.setCategory(category);
                    attraction.setDescription(description);
                    attraction.setAccessible(isAccessible);
                    attraction.setCityName(cityName);
                }
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
    
    public void removeAttractionFromTour(String attractionName, int tourID) throws SQLException {
        String sql = "SELECT Attractions.id FROM ToursCity INNER JOIN Attractions ON ToursCity.attractionID = Attractions.id WHERE Attractions.name = '" + attractionName + "'";
        String attractionID = "";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                attractionID = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	sql = "DELETE FROM ToursCity WHERE attractionID = '" + attractionID + "' AND tourID = '" + tourID + "'";
    	stmt.executeUpdate(sql);
    }
    
    public ArrayList<Attraction> getAttractionsOfCity(String cityName, int tourID){
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
    	String sql = "SELECT id, name, category, description, isAccessible FROM Attractions WHERE cityName = '" + cityName + "'";
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
		
		sql = "SELECT attractionID FROM ToursCity WHERE tourID = '" + tourID + "'";
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
    
   public void addAttractionToTour(String attractionID, int tourID, int time) throws SQLException {
    	String sql = "INSERT INTO  ToursCity (tourID, attractionID, time) VALUES ('"
    			 + tourID + "', " + "'" + attractionID + "', " + "'" + time + "')";

    	stmt.executeUpdate(sql);
    }
    
    
}
