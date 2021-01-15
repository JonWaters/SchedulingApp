package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    @FXML
    private TextField appointmentIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField typeText;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField userIdText;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private ComboBox<?> userComboBox;

    @FXML
    private Spinner<?> startTimeSpinner;

    @FXML
    private Spinner<?> endTimeSpinner;

    @FXML
    void cancelButtonAction(ActionEvent event) {

    }

    @FXML
    void customerComboBoxAction(ActionEvent event) {

    }

    @FXML
    void saveButtonAction(ActionEvent event) {

    }

    @FXML
    void userComboBoxAction(ActionEvent event) {

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

    }
}
