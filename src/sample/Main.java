package sample;

import DAO.CountryDAO;
import Model.Country;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    public static Connection conn = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn = DbConnection.startConnection();
        Country usa = new Country("USA", "Jon", "Jon");
        CountryDAO.create(usa);
        System.out.println(usa.getCountryID());


        launch(args);
    }
}
