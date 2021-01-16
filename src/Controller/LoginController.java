package Controller;

import DAO.UserDAO;
import Main.Main;
import Model.User;
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
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    Locale userLocale = Locale.FRENCH;

    ResourceBundle rb = ResourceBundle.getBundle("Lang/rb", userLocale);

    private TimeZone timeZone = Main.getTimeZone();

    public static User currentUser;

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

            openAppointmentsScreen(event);
        } else {

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

        zoneIdLabel.setText(timeZone.getID());
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
}
