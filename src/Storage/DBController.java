package Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*; 
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBController {
    
    // Create Connection object
    static Connection connection = null;
    //configuration filename
    private static final String FILENAME = "database.properties";
    
    static String dbuser;
    static String dbdatabase;
    static String dbpassword;
    
    public static void DBproperties(){
        Properties prop = new Properties();
        InputStream input;
        try{
            input = new FileInputStream(FILENAME);
            // load a properties file
            prop.load(input);        
            dbuser = prop.getProperty("dbuser");
            dbpassword = prop.getProperty("dbpassword");
            dbdatabase = prop.getProperty("dbdatabase");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean isConnected() {
        try {
            if (connection != null && !connection.isClosed()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void init() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }           
            try {
                connection = DriverManager.getConnection(dbdatabase, dbuser, dbpassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void DeleteAllTables(){
        //Deletes all data from the tables in our database
        try{
            Statement stmt = connection.createStatement();
            System.out.println("Deleting all data in database..");
            String sql1 = "DELETE FROM Shop" ;
            stmt.executeUpdate(sql1);
            
            String sql2 = "DELETE FROM ShopLocation" ;
            stmt.executeUpdate(sql2);
            
            String sql3 = "DELETE FROM OpenHours" ;
            stmt.executeUpdate(sql3);
            
            String sql4 = "DELETE FROM Reviews" ;
            stmt.executeUpdate(sql4);
            System.out.println("DELETED!!!!");
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    public static boolean tableExist() throws SQLException {
        //Checks if a table exists in our database
        boolean tExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, "Shop" , null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals("Shop")) {
                    tExists = true;
                    break;
                }
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return tExists;
    }

}
