package Controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import Main.Main;
import Model.Appointment;
import Model.User;
import Utils.UserLog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller class that provides the control logic for the Login screen.
 *
 * @author Jonathan Waters
 */
public class LoginController implements Initializable {

    /**
     * The user's locale obtained from system default.
     */
    Locale userLocale = Locale.getDefault();

    /**
     * The resource bundle used for language translation.
     */
    ResourceBundle rb = ResourceBundle.getBundle("Lang/rb", userLocale);

    /**
     * The user's timezone obtained from class Main.
     */
    private TimeZone userTimeZone = Main.getUserTimeZone();

    /**
     * The user object of the currently logged in user.
     */
    public static User currentUser;

    /**
     * The date time format.
     */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm");

    /**
     * The user name label for the login screen
     */
    @FXML
    private Label userNameLabel;

    /**
     * The password label for the login screen.
     */
    @FXML
    private Label passwordLabel;

    /**
     * The timezone label for the login screen.
     */
    @FXML
    private Label timezoneLabel;

    /**
     * The timezone ID label for the login screen.
     */
    @FXML
    private Label zoneIdLabel;

    /**
     * The cancel button for the login screen.
     */
    @FXML
    private Button cancelButton;

    /**
     * The login button for the login screen.
     */
    @FXML
    private Button loginButton;

    /**
     * The user name text field for the login screen.
     */
    @FXML
    private TextField userNameText;

    /**
     * The password text field for the login screen.
     */
    @FXML
    private TextField passwordText;

    /**
     * Closes and terminates the application.
     *
     * @param event Cancel button action.
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {

        System.exit(0);
    }

    /**
     * Calls validUser() and openAppointmentsScreen() if user is valid.
     *
     * Calls Userlog.writeLog() to update user log with login attempt details.
     * Calls appointmentReminder() to display reminder message.
     *
     * @param event Login button action.
     * @throws IOException From openAppointmentsScreen().
     * @throws SQLException From appointmentReminder().
     */
    @FXML
    void loginButtonAction(ActionEvent event) throws IOException, SQLException {

        if (validUser()) {

            UserLog.writeLog(1);
            openAppointmentsScreen(event);
            appointmentReminder();
        } else {

            UserLog.writeLog(0);
            loginError();
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setScreenLang();
        setTimeZoneLabel();
    }

    /**
     * Loads AppointmentsController.
     * @param event Passed from parent method.
     * @throws IOException From FXMLLoader.
     */
    private void openAppointmentsScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Uses resource bundle to set language of screen elements based on user locale.
     */
    private void setScreenLang() {

        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        timezoneLabel.setText(rb.getString("timezoneLabel"));
        cancelButton.setText(rb.getString("cancelButton"));
        loginButton.setText(rb.getString("loginButton"));
    }

    /**
     * Sets timezone ID label based on system default timezone.
     *
     */
    private void setTimeZoneLabel() {

        zoneIdLabel.setText(userTimeZone.getID());
    }

    /**
     * Passes user object of currently logged in user to other controllers.
     *
     * @return Current logged in user object.
     */
    public static User getCurrentUser() {

        return currentUser;
    }

    /**
     * Obtains user name and password details from text fields checks credentials against database.
     * @return Boolean.
     * @throws SQLException From UserDAO.selectByName().
     */
    private Boolean validUser() throws SQLException {

        Boolean isValid = false;

        try {

            String userName = userNameText.getText();
            String password = passwordText.getText();
            currentUser = UserDAO.selectByName(userName);

            if (currentUser.getPassword() == null) {
                //Do nothing - needed if user name is not in DB.
            } else if (currentUser.getPassword().equals(password)) {

                isValid = true;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return isValid;
    }

    /**
     * Displays error message for incorrect user name or password.
     */
    private void loginError() {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(rb.getString("error"));
        alert.setHeaderText(rb.getString("errorText"));
        alert.showAndWait();
    }

    /**
     * Checks appointments starting in the next fifth-teen minutes for current user and displays
     * appropriate message.
     *
     * @throws SQLException From AppointmentDAO.selectAll().
     */
    private void appointmentReminder() throws SQLException {

        try {
            boolean upcomingAppointment = false;
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            LocalDateTime now = LocalDateTime.now();
            int currentUserID = currentUser.getUserID();

            for (Appointment appointment : dbAppointments) {

                LocalDateTime startTime = appointment.getStartTime();

                if ((appointment.getUserID() == currentUserID) &&
                        startTime.isAfter(now) &&
                        startTime.isBefore(now.plusMinutes(15))) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information");
                    alert.setHeaderText("Appointment ID " + appointment.getAppointmentID() +
                            " is starting at " + startTime.format(dateTimeFormat) + ".");
                    alert.showAndWait();

                    upcomingAppointment = true;
                    break;
                }
            }

            if (!upcomingAppointment) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information");
                alert.setHeaderText("There are no appointments starting in the next 15 minutes.");
                alert.showAndWait();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
