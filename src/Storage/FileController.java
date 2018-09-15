package Storage;

import Basics.Shop;
import GUI.WrongInputFrame;
import java.io.*;
import java.util.*;
import org.json.simple.parser.ParseException;
import java.awt.Desktop;

public class FileController {
    
    static File curDir = new File(".");
    
    public static boolean checkForFile (File curDir,String locationInput, String termInput) throws IOException, ParseException, ClassNotFoundException {
        //Checks if a specific file exists in our current directory
        File[] filesList = curDir.listFiles();
        boolean exists = false;
        for(File f : filesList){
            if(f.isFile()){
                if(f.getName().equals(""+locationInput+"_"+termInput+".txt")) 
                    exists = true;
            }
        }
        return exists;
    }
    
    public static ArrayList<String> checkAllFiles(File curDir){
        //Prints all .txt files in current directory
        File[] filesList = curDir.listFiles();
        ArrayList<String> CityFiles= new ArrayList<>();
        boolean exists = false;
        for(File f : filesList){
            if (f.getName().endsWith((".txt"))) 
                exists = true;
            
        }
        if ( exists ) {
            for(File f : filesList){
                if (f.getName().endsWith((".txt"))) {
                    CityFiles.add(f.getName());
                }
            }
        }
        return CityFiles;
    }
    
    public static void writeToFile(ArrayList<Shop> shopApiLocal, String locationInput, String termInput) throws IOException, ParseException, ClassNotFoundException{
        //Writes in a file everything he gets from the arraylist
        PrintWriter pw = new PrintWriter(new File(""+locationInput+"_"+termInput+".txt"));
        for (int i = 0 ; i < shopApiLocal.size();i++) {
            //ShopTable
            pw.println(shopApiLocal.get(i).getName());
            pw.println(shopApiLocal.get(i).getId());
            pw.println(shopApiLocal.get(i).getPrice());
            pw.println(shopApiLocal.get(i).getReview_count());
            pw.println(shopApiLocal.get(i).getRating());
            pw.println(shopApiLocal.get(i).getPhone());
            pw.println(shopApiLocal.get(i).isIs_open_now());
            pw.println(shopApiLocal.get(i).getHours_type());
            pw.println(shopApiLocal.get(i).getDisplay_phone());
            pw.println(shopApiLocal.get(i).getUrl());
            pw.println(shopApiLocal.get(i).getImage_url());
            pw.println(shopApiLocal.get(i).isIs_closed());
            pw.println(shopApiLocal.get(i).isIs_claimed());
            //ShopLoacationTable
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getCity());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getCountry());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getState());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getZip_code());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getAddress1());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getAddress2());
            pw.println(shopApiLocal.get(i).getShopLocationList().get(0).getAddress3());
            //OpenHoursTable
            for(int j=0; j<shopApiLocal.get(i).getWorkingHours().size(); j++){
                pw.println(shopApiLocal.get(i).getWorkingHours().get(j).getDay());
                pw.println(shopApiLocal.get(i).getWorkingHours().get(j).getFrom());
                pw.println(shopApiLocal.get(i).getWorkingHours().get(j).getTo());
                pw.println(shopApiLocal.get(i).isIs_overnight());
            }
            //CategoriesTable
            pw.println(Arrays.deepToString(shopApiLocal.get(i).getCategories()));
            //PhotosTable
            pw.println(Arrays.toString(shopApiLocal.get(i).getPhotos()));
            for ( int j = 0 ; j <shopApiLocal.get(i).getReviewsList().size() ; j++ ) {
                pw.println(shopApiLocal.get(i).getReviewsList().get(j).getText());
            }
            pw.println("##############################################################");
        }
        pw.close();
    }
    
    public static void ReadFromFile(String locationInput, String termInput) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException{
        //Reads from a file whose name is given as an attribute
        File file = new File(""+locationInput+"_"+termInput+".txt");
        if(checkForFile(curDir, locationInput, termInput)){
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } else {
            new WrongInputFrame().setVisible(true); 
        }
    }
    
}

