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

public class LoginController implements Initializable {

    Locale userLocale = Locale.getDefault();

    ResourceBundle rb = ResourceBundle.getBundle("Lang/rb", userLocale);

    private TimeZone userTimeZone = Main.getUserTimeZone();

    public static User currentUser;

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm");

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label timezoneLabel;

    @FXML
    private Label zoneIdLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField userNameText;

    @FXML
    private TextField passwordText;

    @FXML
    void cancelButtonAction(ActionEvent event) {

        System.exit(0);
    }

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

    private void openAppointmentsScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void setScreenLang() {

        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        timezoneLabel.setText(rb.getString("timezoneLabel"));
        cancelButton.setText(rb.getString("cancelButton"));
        loginButton.setText(rb.getString("loginButton"));
    }

    private void setTimeZoneLabel() {

        zoneIdLabel.setText(userTimeZone.getID());
    }

    public static User getCurrentUser() {

        return currentUser;
    }

    private Boolean validUser() throws SQLException {

        Boolean isValid = false;

        try {

            String userName = userNameText.getText();
            String password = passwordText.getText();
            currentUser = UserDAO.selectByName(userName);

            if (currentUser.getPassword() == null) {
                //Do nothing
            } else if (currentUser.getPassword().equals(password)) {

                isValid = true;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return isValid;
    }

    private void loginError() {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(rb.getString("error"));
        alert.setHeaderText(rb.getString("errorText"));
        alert.showAndWait();
    }

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
