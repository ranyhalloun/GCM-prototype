package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import commands.SigninCommand;
import application.Main;


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
    
    //check if input error!!
    public void insertNewMap(String cityName, int id, String description, String imagePath) throws SQLException {
    	if(!cityExist(cityName))
    		insertNewCity(cityName, 1);
    	String sql = "INSERT INTO Maps (cityName, id, description, imagePath) VALUES ('"
    	    	+ cityName + "', " + "'" + id + "', " + "'" + description + "', " + "'" + imagePath + "')";
    	stmt.executeUpdate(sql);
    }
    
    public void insertNewCity(String name, int version) throws SQLException {
    	String sql = "INSERT INTO Cities (name, version) VALUES ('"
    	    	+ name + "', " + "'" + version + "')";
    	stmt.executeUpdate(sql);
    }
    
    // Should we have return value?
    // What to do incase of error?
    public void registerNewCustomer(String firstname, String lastname, String username, String password, String email, String phone) throws SQLException
    {
    	String sql = "INSERT INTO Users (username, password, firstName, lastName, emailAddress, phoneNumber) VALUES ('"
    	+ username + "', " + "'" + password + "', " + "'" + firstname + "', " + "'" + lastname + "', " + "'" + email + "', " + "'" + phone + "')";
    	stmt.executeUpdate(sql);
    	
    	sql = "INSERT INTO Customers (customerUsername) VALUES ('"
    	+ username + "')";
    	stmt.executeUpdate(sql);
    }
    
    public boolean authenticate(String username, String password, SigninCommand signinCommand) throws SQLException
    {
        boolean success = false;
        String sql = "SELECT username, password , role FROM Users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
            success = password.equals(rs.getString("password"));
        	signinCommand.setRole((rs.getString("role")));
        return success;
    }
    
    public boolean usernameExist(String username) throws SQLException{
        
        String sql = "SELECT username FROM Users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
            
        return rs.next();
    }
    
    public boolean cityExist(String cityName) throws SQLException {
    	String sql = "SELECT name FROM Cities WHERE name = '" + cityName + "'";
        ResultSet rs = stmt.executeQuery(sql);
            
        return rs.next();
    }
    
    
   public boolean searchMap(String attraction, String cityName, String description) throws SQLException {
    	boolean success = false;
    	String sql;
    	ResultSet rs;
    	int counter = 0;
    	if(!cityName.isEmpty() && attraction.isEmpty() && description.isEmpty()) {
            success = true;
    		
    		sql = "SELECT cityName, description FROM Maps WHERE cityName = '" + cityName + "'";
    		rs = stmt.executeQuery(sql);
            System.out.println("Maps and their description:");
    		while(rs.next()) {
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
            
    	else if(cityName.isEmpty() && !attraction.isEmpty() && description.isEmpty()) {
            success = true;

    		sql = "SELECT cityName, description FROM Attractions WHERE name = '" + attraction + "'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("cityName and attraction's description: ");
    		while(rs.next())
            	System.out.println(rs.getString(1) + " " + rs.getString(2));
            
            sql = "SELECT COUNT(attractionName) FROM AttractionsMaps WHERE attractionName = '" + attraction + "'";
    		rs = stmt.executeQuery(sql);
    		rs.next();
            System.out.println("#Maps have this attraction: " + rs.getString(1));
            
            
    	}
    	
    	else if(cityName.isEmpty() && attraction.isEmpty() && !description.isEmpty()) {
            success = true;    		
            
            sql = "SELECT cityName, description FROM Maps WHERE description LIKE '%" + description + "%'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("cityName and map's description: ");
    		while(rs.next())
            	System.out.println(rs.getString(1) + " " + rs.getString(2));
    	}
    	
    	else if(!cityName.isEmpty() && !attraction.isEmpty() && description.isEmpty()) {
    		success = true;
    		
    		sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '" + attraction + "' AND Maps.cityName = '" + cityName + "'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("Maps description:");
    		while(rs.next())
            	System.out.println(rs.getString(1));
    	}
    	
    	else if(!cityName.isEmpty() && attraction.isEmpty() && !description.isEmpty()) {
    		success = true;
    		
    		sql = "SELECT description FROM Maps WHERE description LIKE '%" + description + "%' AND cityName = '" + cityName + "'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("Maps description:");
    		while(rs.next())
            	System.out.println(rs.getString(1));
    	}
    	
    	else if(cityName.isEmpty() && !attraction.isEmpty() && !description.isEmpty()) {
    		success = true;
    		
    		sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '" + attraction + "' AND Maps.description LIKE '%" + description + "%'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("Maps description:");
    		while(rs.next())
            	System.out.println(rs.getString(1));
    	}
    	
    	else if(!cityName.isEmpty() && !attraction.isEmpty() && !description.isEmpty()) {
    		success = true;
    		
    		sql = "SELECT Maps.description FROM Maps INNER JOIN AttractionsMaps ON Maps.id = AttractionsMaps.mapID INNER JOIN Attractions ON Attractions.name = AttractionsMaps.attractionName  WHERE Attractions.name = '" + attraction + "' AND Maps.description LIKE '%" + description + "%' AND Maps.cityName = '" + cityName + "'";
    		rs = stmt.executeQuery(sql);
    		System.out.println("Maps description:");
    		while(rs.next())
            	System.out.println(rs.getString(1));
    	}
    	
    	
        return success;
    }
   
   
    
//    public int getNumOfPurchases(String username) throws SQLException
//    {
//        int purchases = -1;
//        String sql = "SELECT username, numOfPurchases FROM Customers WHERE username = '" + username + "'";
//        ResultSet rs = stmt.executeQuery(sql);
//        if (rs.next())
//            purchases = rs.getInt("numOfPurchases");
//        return purchases;
//    }
//    
//    public void incNumOfPurchases(String username) throws SQLException
//    {
//        String sql = "SELECT username, numOfPurchases FROM Customers WHERE username = '" + username + "'";
//        ResultSet rs = stmt.executeQuery(sql);
//        if (rs.next()) {
//            rs.updateInt("numOfPurchases", rs.getInt("numOfPurchases") + 1);
//            rs.updateRow();
//        }
//    }
    
    
    
}
