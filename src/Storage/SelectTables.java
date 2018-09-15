package Storage;

import Basics.*;
import static Storage.DBController.connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SelectTables {

    public static ArrayList<String> SelectSpecCol(){
        try{
            Statement stmt = connection.createStatement(); 
            //select the combinations of location and category that exist in db
            String sql = "SELECT CITY , CATEGORY FROM Shop " ;
            stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            ArrayList<String> SpecArray = new ArrayList<>();
            String tmpCity = null;
            String tmpCateg = null;
            int k = 0;
            while (rs.next()) { // check if table has more lines
                if ( k == 0 ) { // if its the first line
                    tmpCity = rs.getString(1); 
                    tmpCateg = rs.getString(2); 
                    String Shop = ""+rs.getString(1)+"_"+rs.getString(2)+"";
                    SpecArray.add(Shop);
                }
                
                if ( tmpCateg != null) {
                    if ( !rs.getString(1).equals (tmpCity) || !rs.getString(2).equals(tmpCateg) ) { // if there is any change in city or category create another shop object
                        tmpCity = rs.getString(1);
                        tmpCateg = rs.getString(2);
                        String Shop = ""+rs.getString(1)+"_"+rs.getString(2)+"";
                        SpecArray.add(Shop);
                    }
                }
                k++;
            }
            // delete dublicate insertions
            Set<String> hs = new HashSet<>();
            hs.addAll(SpecArray);
            SpecArray.clear();
            SpecArray.addAll(hs);
            return SpecArray;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Shop> SelectShop(ArrayList<Shop> DatabaseArraylist, String cityInput, String termInput, Boolean checked){
        //Getting info from shop table
        try{
            Statement stmt = connection.createStatement();
            String sql=null;
            //if ratio button is checked
            if(checked){
                sql = "SELECT * FROM Shop " +
                         "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND OPEN_NOW IN (1)" ;
            }else{
                sql = "SELECT * FROM Shop " +
                         "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"')" ;
            }
            stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){ // check if table has more lines
                Shop currentShop = new Shop();
                currentShop.setName(rs.getString(2));
                currentShop.setId(rs.getString(3));
                currentShop.setPrice(rs.getString(4));
                currentShop.setReview_count(rs.getLong(5));
                currentShop.setRating(rs.getDouble(6));
                currentShop.setPhone(rs.getString(7));
                currentShop.setHours_type(rs.getString(8));
                currentShop.setDisplay_phone(rs.getString(9));
                currentShop.setUrl(rs.getString(10));
                currentShop.setImage_url(rs.getString(11));
                DatabaseArraylist.add(currentShop);
            }
            return DatabaseArraylist;
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Shop> SelectShopLocation(ArrayList<Shop> DatabaseArraylist, String cityInput, String termInput, Boolean checked){
        //Getting info from shopLocation table
        try{
            Statement stmt = connection.createStatement(); 
            String sql=null;
            //if ratio button is checked
            if(checked){
                sql = "SELECT * FROM ShopLocation " +
                         "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND OPEN_NOW IN (1)" ;
            }else{
                sql = "SELECT * FROM ShopLocation " +
                         "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"')" ;
            }
                      
            stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            // for every shop object create an shopLocation arraylist
            for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                ArrayList<ShopLocation> tempShopLoc = new ArrayList<>();
                DatabaseArraylist.get(y).setShopLocationList(tempShopLoc);
            }
            while(rs.next()){ // check if table has more lines
                ShopLocation tmpShopLoc = new ShopLocation(rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(4),rs.getString(7),rs.getString(8));
                DatabaseArraylist.get(i).getShopLocationList().add(tmpShopLoc);
                i++;
            }
            return DatabaseArraylist;
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Shop> SelectOpenHours(ArrayList<Shop> DatabaseArraylist, String cityInput, String termInput, Boolean checked){
        //Getting info from OpenHours table
        try{
            Statement stmt = connection.createStatement(); 
             // for every shop object create an OpenHours arraylist
            for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                ArrayList<OpenHours> tempOpenHours = new ArrayList<>();
                DatabaseArraylist.get(y).setWorkingHours(tempOpenHours);
            }
            String sql=null;
            //if ratio button is checked
            if(checked){
                for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                    sql = "SELECT * FROM OpenHours " +
                        "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND OPEN_NOW IN (1) AND ID IN ('"+DatabaseArraylist.get(y).getId()+"')";
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        OpenHours currentOpenHours = new OpenHours(rs.getShort(3),rs.getString(4),rs.getString(5));
                        DatabaseArraylist.get(y).getWorkingHours().add(currentOpenHours);
                    }
                }
            }else{
                for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                    sql = "SELECT * FROM OpenHours " +
                        "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND ID IN ('"+DatabaseArraylist.get(y).getId()+"')";
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        OpenHours currentOpenHours = new OpenHours(rs.getShort(3),rs.getString(4),rs.getString(5));
                        DatabaseArraylist.get(y).getWorkingHours().add(currentOpenHours);
                    }
                }
            }
            return DatabaseArraylist;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Shop> SelectReviews(ArrayList<Shop> DatabaseArraylist, String cityInput ,String termInput, Boolean checked){
        //Getting info from reviews table
        try{
            Statement stmt = connection.createStatement(); 
            for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                ArrayList<Reviews> tempRev = new ArrayList<>();
                DatabaseArraylist.get(y).setReviewsList(tempRev);
            }
            String sql=null;
            //if ratio button is checked
            if(checked){
                for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                    sql = "SELECT * FROM Reviews " +
                             "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND Open_now in (1) AND ID IN ('"+DatabaseArraylist.get(y).getId()+"')" ;
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        Reviews currentRev = new Reviews(rs.getString(3));
                        DatabaseArraylist.get(y).getReviewsList().add(currentRev);
                    }
                }
            }else{
                for ( int y = 0 ; y < DatabaseArraylist.size(); y++) {
                    sql = "SELECT * FROM Reviews " +
                             "WHERE CITY IN ('"+cityInput+"') AND CATEGORY IN ('"+termInput+"') AND ID IN ('"+DatabaseArraylist.get(y).getId()+"')" ;
                    stmt.executeUpdate(sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    while(rs.next()) {
                        Reviews currentRev = new Reviews(rs.getString(3));
                        DatabaseArraylist.get(y).getReviewsList().add(currentRev);
                    }
                }
            }
            return DatabaseArraylist;
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Shop> SelectAllTables (ArrayList<Shop> DatabaseArraylist, String cityInput, String termInput, Boolean checked){
        //Filling the Database Arraylist with all the info fetched from the select methods above
        DatabaseArraylist = SelectShop(DatabaseArraylist, cityInput,termInput,checked);
        DatabaseArraylist = SelectShopLocation(DatabaseArraylist,cityInput,termInput,checked);
        DatabaseArraylist = SelectOpenHours(DatabaseArraylist, cityInput,termInput,checked);
        DatabaseArraylist = SelectReviews(DatabaseArraylist, cityInput,termInput,checked);
        return DatabaseArraylist;
    }
    
}
