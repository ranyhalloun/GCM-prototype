package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    
    public boolean searchMap(String attraction, String cityName, String description) throws SQLException {
        boolean success = false;
        String sql;
        ResultSet rs;
        int counter = 0;
        if (!cityName.isEmpty() && attraction.isEmpty() && description.isEmpty()) {
            success = true;

            sql = "SELECT cityName, description FROM Maps WHERE cityName = '" + cityName + "'";
            rs = stmt.executeQuery(sql);
            System.out.println("Maps and their description:");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }

            sql = "SELECT COUNT(cityName) FROM Attractions WHERE cityName = '" + cityName + "'";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("#Attractions: " + rs.getString(1));

            sql = "SELECT COUNT(cityName) FROM ToursCity WHERE cityName = '" + cityName + "'";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("#Tours: " + rs.getString(1));
        }

        else if (cityName.isEmpty() && !attraction.isEmpty() && description.isEmpty()) {
            success = true;

            sql = "SELECT cityName, description FROM Attractions WHERE name = '" + attraction + "'";
            rs = stmt.executeQuery(sql);
            System.out.println("cityName and attraction's description: ");
            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2));

            sql = "SELECT COUNT(attractionName) FROM AttractionsMaps WHERE attractionName = '" + attraction + "'";
            rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println("#Maps have this attraction: " + rs.getString(1));
        }

        else if (cityName.isEmpty() && attraction.isEmpty() && !description.isEmpty()) {
            success = true;

            sql = "SELECT cityName, description FROM Maps WHERE description LIKE '%" + description + "%'";
            rs = stmt.executeQuery(sql);
            System.out.println("cityName and map's description: ");
            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2));
        }

        else if (!cityName.isEmpty() && !attraction.isEmpty() && description.isEmpty()) {
            success = true;

            sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '"
                    + attraction + "' AND Maps.cityName = '" + cityName + "'";
            rs = stmt.executeQuery(sql);
            System.out.println("Maps description:");
            while (rs.next())
                System.out.println(rs.getString(1));
        }

        else if (!cityName.isEmpty() && attraction.isEmpty() && !description.isEmpty()) {
            success = true;
            
            sql = "SELECT description FROM Maps WHERE description LIKE '%" + description + "%' AND cityName = '"
                    + cityName + "'";
            rs = stmt.executeQuery(sql);
            System.out.println("Maps description:");
            while (rs.next())
                System.out.println(rs.getString(1));
        }

        else if (cityName.isEmpty() && !attraction.isEmpty() && !description.isEmpty()) {
            success = true;

            sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '"
                    + attraction + "' AND Maps.description LIKE '%" + description + "%'";
            rs = stmt.executeQuery(sql);
            System.out.println("Maps description:");
            while (rs.next())
                System.out.println(rs.getString(1));
        }

        else if (!cityName.isEmpty() && !attraction.isEmpty() && !description.isEmpty()) {
            success = true;

            sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '"
                    + attraction + "' AND Maps.description LIKE '%" + description + "%' AND Maps.cityName = '"
                    + cityName + "'";
            rs = stmt.executeQuery(sql);
            System.out.println("Maps description:");
            while (rs.next())
                System.out.println(rs.getString(1));
        }

        return success;
    }
    
    public Customer getCustomerInfo(Object object) throws SQLException {
        String sql = "SELECT username, password, firstName, lastName, emailAddress, PhoneNumber FROM Users WHERE username = '" + object + "'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
    }
    
    public ArrayList<String> getCitiesQueue() throws SQLException {
        ArrayList<String> cities = new ArrayList<String>();
    	String sql = "SELECT name FROM CitiesQueue";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())
            cities.add(rs.getString(1));
        return cities;
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
}
