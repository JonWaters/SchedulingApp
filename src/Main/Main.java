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

/**
 * The appointment scheduling program implements an application for managing
 * a user's appointments with customers.
 *
 * @author Jonathan Waters
 */
public class Main extends Application {

    /**
     * The database connection object.
     */
    public static Connection conn = null;

    /**
     * The user's TimeZone.
     */
    public static TimeZone userTimeZone = TimeZone.getDefault();

    /**
     * The start method creates the FXML stage and loads the initial scene.
     *
     * @param primaryStage The FXML stage.
     * @throws Exception From FXMLLoader
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Getter for the user's TimeZone.
     *
     * @return TimeZone object.
     */
    public static TimeZone getUserTimeZone() {

        return userTimeZone;
    }

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments.
     * @throws ClassNotFoundException From DbConnection.startConnection().
     * @throws SQLException From DbConnection.startConnection().
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        conn = DbConnection.startConnection();

        launch(args);
    }
}
