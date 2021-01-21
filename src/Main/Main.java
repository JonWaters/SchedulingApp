package Main;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.AppointmentDisplay;
import Model.Customer;
import Model.CustomerDisplay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

public class Main extends Application {

    public static Connection conn = null;

    public static TimeZone userTimeZone = TimeZone.getDefault();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static TimeZone getUserTimeZone() {

        return userTimeZone;
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn = DbConnection.startConnection();

        launch(args);
    }
}
