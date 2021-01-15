package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    public static Connection conn = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn = DbConnection.startConnection();
        /*
        Country usa = new Country();
        usa = CountryDAO.selectByID(4);

        usa.setLastUpdatedBy("Steve");

        CountryDAO.delete(usa);

        usa = CountryDAO.selectByID(4);

        System.out.println(usa.getCountryID());
        System.out.println(usa.getCountryName());
        System.out.println(usa.getCreateDate());
        System.out.println(usa.getCreatedBy());
        System.out.println(usa.getLastUpdateTime());
        System.out.println(usa.getLastUpdatedBy());
        */

        launch(args);
    }
}
