package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static private final String DB = "wgjWrab1HZ";
    static private final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
    static private final String USER = "wgjWrab1HZ";
    static private final String PASS = "RElRKksRJo";
    
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
    // What to do incase of error?
    public void registerNewCustomer(String firstname, String lastname, String username, String password) throws SQLException
    {
        String sql = "INSERT INTO users (firstname, lastname, username, password) VALUES ('" + firstname + "', "
                + "'" + lastname + "', '" + username + "', '" + password + "')";
        stmt.executeUpdate(sql);
    }
    
    public boolean authenticate(String username, String password) throws SQLException
    {
        boolean success = false;
        String sql = "SELECT username, password FROM users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
            success = password.equals(rs.getString("password"));
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
