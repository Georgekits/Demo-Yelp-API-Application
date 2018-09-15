package Main;

import Basics.Shop;
import Extra.YelpJsonApi;
import static Extra.properties.InitializeConfigParameters;
import GUI.MainFrame;
import Storage.DBController;
import static Storage.DBController.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import org.jsoup.HttpStatusException;

public class Demo {
    
    //ATTRIBUTE FOR CURRENT DIRECTORY
    static ArrayList<Shop> shopApiLocal = new ArrayList<>();

    public static ArrayList<Shop> fetchFromApi(String termInput,String locationInput) throws IOException, ParseException, ClassNotFoundException, SQLException {
        //GET INFO FROM APIs  AND COPIES THEM TO AN ARRAYLIST
        try {
            shopApiLocal = YelpJsonApi.SearchForShops(termInput, locationInput);
            for (int i = 0; i < shopApiLocal.size(); i++) {
                YelpJsonApi.FetchShop(shopApiLocal.get(i));
            }
            for (int i = 0; i < shopApiLocal.size(); i++) {
                YelpJsonApi.GetReviews(shopApiLocal.get(i));
            }
            return shopApiLocal;
        } catch (NullPointerException e) {
            System.out.println("API was not able to fetch info with your input!");
        } catch (HttpStatusException e) {
            System.out.println("API was not able to fetch info with your input!");
        } catch (IOException e) {
            System.out.println("API was not able to fetch info with your input!");
        }
        return null;
    }

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException  {
        //Loads all the parameters from .properties files
        InitializeConfigParameters();
        DBproperties();
        //Initialize Connection
        DBController.init();
        //Load the Main Frame
        new MainFrame().setVisible(true);
        
    }
}
