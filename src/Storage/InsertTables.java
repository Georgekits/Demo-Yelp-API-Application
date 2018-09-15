package Storage;

import Basics.Shop;
import static Storage.DBController.connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertTables {
    
    public static void InsertShop(ArrayList<Shop> ShopArraylist,String termInput){
       //Insert data in shop table
        try {
            PreparedStatement sql = connection.prepareStatement("INSERT INTO Shop VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");  
            for ( int i = 0 ; i < ShopArraylist.size(); i++) {
                sql.setString(1,ShopArraylist.get(i).getShopLocationList().get(0).getCity());
                sql.setString(2,ShopArraylist.get(i).getName());
                sql.setString(3,ShopArraylist.get(i).getId());
                sql.setString(4,ShopArraylist.get(i).getPrice());
                sql.setLong(5,ShopArraylist.get(i).getReview_count());
                sql.setDouble(6,ShopArraylist.get(i).getRating());
                sql.setString(7,ShopArraylist.get(i).getPhone());
                sql.setString(8,ShopArraylist.get(i).getHours_type());
                sql.setString(9,ShopArraylist.get(i).getDisplay_phone());
                sql.setString(10,ShopArraylist.get(i).getUrl());
                sql.setString(11,ShopArraylist.get(i).getImage_url());
                sql.setString(12,termInput);
                sql.setBoolean(13, ShopArraylist.get(i).isIs_open_now());
                sql.addBatch();
            }
            sql.executeBatch();           
            sql.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static void InsertShopLocation(ArrayList<Shop> ShopArraylist,String termInput){
       //Insert data in shopLocation table
        try {
            PreparedStatement sql = connection.prepareStatement("INSERT INTO ShopLocation VALUES(?,?,?,?,?,?,?,?,?,?)");  
            for ( int i = 0 ; i < ShopArraylist.size(); i++) {
                sql.setString(1,ShopArraylist.get(i).getId());
                sql.setString(2,ShopArraylist.get(i).getShopLocationList().get(0).getCity());
                sql.setString(3,ShopArraylist.get(i).getShopLocationList().get(0).getCountry());
                sql.setString(4,ShopArraylist.get(i).getShopLocationList().get(0).getState());
                sql.setString(5,ShopArraylist.get(i).getShopLocationList().get(0).getZip_code());
                sql.setString(6,ShopArraylist.get(i).getShopLocationList().get(0).getAddress1());
                sql.setString(7,ShopArraylist.get(i).getShopLocationList().get(0).getAddress2());
                sql.setString(8,ShopArraylist.get(i).getShopLocationList().get(0).getAddress3());
                sql.setString(9,termInput);
                sql.setBoolean(10, ShopArraylist.get(i).isIs_open_now());
                sql.addBatch();
            }
            sql.executeBatch();           
            sql.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void InsertOpenHours(ArrayList<Shop> ShopArraylist,String termInput){
        //Insert data in OpenHours table
        try {
            PreparedStatement sql = connection.prepareStatement("INSERT INTO OpenHours VALUES(?,?,?,?,?,?,?)");  
            for ( int i = 0 ; i < ShopArraylist.size(); i++) {
                for(int j=0; j< ShopArraylist.get(i).getWorkingHours().size(); j++){ 
                    sql.setString(1,ShopArraylist.get(i).getShopLocationList().get(0).getCity());
                    sql.setString(2,ShopArraylist.get(i).getId());
                    sql.setLong(3,ShopArraylist.get(i).getWorkingHours().get(j).getDay());
                    sql.setString(4,ShopArraylist.get(i).getWorkingHours().get(j).getFrom());
                    sql.setString(5,ShopArraylist.get(i).getWorkingHours().get(j).getTo());
                    sql.setString(6,termInput);
                    sql.setBoolean(7, ShopArraylist.get(i).isIs_open_now());
                    sql.addBatch();
                }
            }
            sql.executeBatch();           
            sql.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void InsertReviews(ArrayList<Shop> ShopArraylist,String termInput){
        //Insert data in Reviews table
        try {
            PreparedStatement sql = connection.prepareStatement("INSERT INTO Reviews VALUES(?,?,?,?,?)");  
            for ( int i = 0 ; i < ShopArraylist.size(); i++) {
                for ( int j = 0 ; j < 3; j++) {
                    sql.setString(1,ShopArraylist.get(i).getShopLocationList().get(0).getCity());
                    sql.setString(2,ShopArraylist.get(i).getId());
                    sql.setString(3,ShopArraylist.get(i).getReviewsList().get(j).getText());
                    sql.setString(4,termInput);
                    sql.setBoolean(5, ShopArraylist.get(i).isIs_open_now());
                    sql.addBatch();
                }
            }
            sql.executeBatch();           
            sql.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void InsertAll(ArrayList<Shop> ShopArraylist,String termInput){
        //Insert data in all tables
        InsertShop(ShopArraylist,termInput);
        InsertShopLocation(ShopArraylist,termInput);
        InsertOpenHours(ShopArraylist,termInput);
        InsertReviews(ShopArraylist,termInput);
    }
    
   
}
