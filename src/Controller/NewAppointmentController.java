package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utils.VerifyAppt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller class that provides the control logic for the New Appointment screen.
 *
 * @author Jonathan Waters
 */
public class NewAppointmentController implements Initializable {

    /**
     * The date time format.
     */
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * The currently logged in user object from LoginController.
     */
    private User currentUser = LoginController.getCurrentUser();

    /**
     * A list of contact objects for the contact combo box.
     */
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * A list of customer objects for the customer combo box.
     */
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * A list of user objects for the user combo box.
     */
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * The contact object selected in the contact combo box.
     */
    private Contact selectedContact;

    /**
     * The customer object selected in the customer combo box.
     */
    private Customer selectedCustomer;

    /**
     * The user object selected in the user combo box.
     */
    private User selectedUser;

    /**
     * The title text field.
     */
    @FXML
    private TextField titleText;

    /**
     * The description text field.
     */
    @FXML
    private TextField descriptionText;

    /**
     * The location text field.
     */
    @FXML
    private TextField locationText;

    /**
     * The type text field
     */
    @FXML
    private TextField typeText;

    /**
     * The start date picker object.
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The end date picker object.
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * The customer ID text field.
     */
    @FXML
    private TextField customerIdText;

    /**
     * The user ID text field.
     */
    @FXML
    private TextField userIdText;

    /**
     * The contact combo box.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * The customer combo box.
     */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * The user combo box.
     */
    @FXML
    private ComboBox<User> userComboBox;

    /**
     * The start time spinner object.
     */
    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    /**
     * The end time spinner object.
     */
    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    /**
     * Displays confirmation message and loads AppointmentsController.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the Appointments screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Sets value for selectedCustomer object and value for customer ID text field.
     *
     * @param event Customer combo box action.
     */
    @FXML
    void customerComboBoxAction(ActionEvent event) {

        selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
    }

    /**
     * Creates new appointment object and saves to database.
     *
     * Input validation is performed with methods from VerifyAppt.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();
            LocalDateTime startTime = getStartDateTime();
            LocalDateTime endTime = getEndDateTime();
            String createdBy = currentUser.getUserName();
            String lastUpdatedBy = currentUser.getUserName();
            int customerID = selectedCustomer.getCustomerID();
            int userID = selectedUser.getUserID();
            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();


            Appointment newAppointment = new Appointment(title, description, location, type,
                    startTime, endTime, createdBy, lastUpdatedBy, customerID, userID, contactID);

            if (VerifyAppt.overlappingAppointment(newAppointment)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("The appointment you are trying to create overlaps with " +
                        "another appointment.");
                alert.showAndWait();
            }

            if (!VerifyAppt.fieldsEmpty(newAppointment) &&
                    !VerifyAppt.startAfterEnd(newAppointment) &&
                    !VerifyAppt.outsideBusinessHours(newAppointment) &&
                    !VerifyAppt.overlappingAppointment(newAppointment)) {

                AppointmentDAO.create(newAppointment);

                Parent parent = FXMLLoader.load(getClass().getResource("/View/Appointments.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        }
        catch(NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("One or more fields are not selected.");
            alert.showAndWait();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets value for selectedUser object and value of user ID text field.
     *
     * @param event User combo box action.
     */
    @FXML
    void userComboBoxAction(ActionEvent event) {

        selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        userIdText.setText(String.valueOf(selectedUser.getUserID()));
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

        setDefaultValues();
    }

    /**
     * Sets default values for timer spinners and combo boxes.
     */
    private void setDefaultValues() {

        startSVF.setValue(LocalTime.of(8, 00));
        startTimeSpinner.setValueFactory(startSVF);
        endSVF.setValue(LocalTime.of(22, 00));
        endTimeSpinner.setValueFactory(endSVF);

        try {
            contactList = ContactDAO.selectAll();
            contactComboBox.setItems(contactList);

            customerList = CustomerDAO.selectAll();
            customerComboBox.setItems(customerList);

            userList = UserDAO.selectAll();
            userComboBox.setItems(userList);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates LocalDateTime object based on values of start date picker and start time spinner.
     *
     * @return LocalDateTime object.
     */
    private LocalDateTime getStartDateTime() {

        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime;
    }

    /**
     * Creates LocalDateTime object based on values of end date picker and end time spinner.
     *
     * @return LocalDateTime object.
     */
    private LocalDateTime getEndDateTime() {

        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return endDateTime;
    }

    /**
     * Value factory for start time spinner based on fifth-teen minute intervals.
     *
     */
    SpinnerValueFactory startSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat,null));
        }
        @Override public void decrement(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
        }
        @Override public void increment(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));
        }
    };

    /**
     * Value factory for end time spinner based on fifth-teen minute intervals.
     *
     */
    SpinnerValueFactory endSVF = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(timeFormat,null));
        }
        @Override public void decrement(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.plusHours(steps));
            setValue(localTime.plusMinutes(steps + 14));
        }
        @Override public void increment(int steps) {
            LocalTime localTime = (LocalTime) getValue();
            setValue(localTime.minusHours(steps));
            setValue(localTime.minusMinutes(16 - steps));
        }
    };
}
