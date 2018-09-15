package Storage;

import static Storage.DBController.connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
    
    public static void createAllTables(){
        //Creates all tables
        try{
            Statement stmt = connection.createStatement();
            //CREATE TABLE Shop
            String sql1 = "CREATE TABLE Shop (" +
                   " city VARCHAR(30), " +
                   " name VARCHAR(100) NOT NULL, " +
                   " id VARCHAR(100), " +
                   " price VARCHAR(20), " + 
                   " review_count int, " + 
                   " rating int, " +
                   " phone VARCHAR(15), " +
                   " hours_type VARCHAR(20), " +
                   " display_phone VARCHAR(20), " +
                   " url VARCHAR(300), " +                 
                   " image_url VARCHAR(100), " +
                   " category VARCHAR(30), " +
                   " open_now VARCHAR(20) CHECK ( open_now=0 OR open_now=1)" +
                   " ) ";
            stmt.executeUpdate(sql1);
            //CREATE TABLE ShopLocation
            String sql2 = "CREATE TABLE ShopLocation " +
                    "(id VARCHAR(100), " +
                    " city VARCHAR(30), " +
                    " country VARCHAR(30), " +
                    " state VARCHAR(30), " +
                    " zip_code VARCHAR(20), " +
                    " address_1 VARCHAR(50), " +
                    " address_2 VARCHAR(50), " +
                    " address_3 VARCHAR(50),  " +
                    " category VARCHAR(30), " +
                    " open_now VARCHAR(20) CHECK ( open_now=0 OR open_now=1))";
            stmt.executeUpdate(sql2); 
            //CREATE TABLE OpenHours
            String sql3 = "CREATE TABLE OpenHours (" +
                    " city VARCHAR(40), " +
                    " id VARCHAR(100), " + 
                    " day int, " +
                    " starting1 VARCHAR(4)," +
                    " ending VARCHAR(10), " +
                    " category VARCHAR(30), " +
                    " open_now VARCHAR(20) CHECK ( open_now=0 OR open_now=1)"+
                    " ) ";
            stmt.executeUpdate(sql3);
            //CREATE TABLE Reviews    
            String sql5 = "CREATE TABLE Reviews " +
                    "(city VARCHAR(30), " +
                    " id VARCHAR(100), " +
                    " text VARCHAR(2000), " +
                    " category VARCHAR(30), " +
                    " open_now VARCHAR(20) CHECK ( open_now=0 OR open_now=1))";
            stmt.executeUpdate(sql5);
           
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
        }
        
    }

}
