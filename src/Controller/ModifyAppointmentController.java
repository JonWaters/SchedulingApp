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

public class ModifyAppointmentController implements Initializable {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private User currentUser = LoginController.getCurrentUser();

    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private Appointment selectedAppointment;

    private Contact selectedContact;

    private Customer selectedCustomer;

    private User selectedUser;

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
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the Appointments screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void customerComboBoxAction(ActionEvent event) {

        selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();
            LocalDateTime startTime = getStartDateTime();
            LocalDateTime endTime = getEndDateTime();
            String createdBy = selectedAppointment.getCreatedBy();
            String lastUpdatedBy = currentUser.getUserName();
            int customerID = selectedCustomer.getCustomerID();
            int userID = selectedUser.getUserID();
            int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();


            Appointment newAppointment = new Appointment(title, description, location, type,
                    startTime, endTime, createdBy, lastUpdatedBy, customerID, userID, contactID);

            newAppointment.setAppointmentID(selectedAppointment.getAppointmentID());

            if (!VerifyAppt.fieldsEmpty(newAppointment) &&
                    !VerifyAppt.startAfterEnd(newAppointment) &&
                    !VerifyAppt.outsideBusinessHours(newAppointment) &&
                    !VerifyAppt.overlappingAppointment(newAppointment)) {

                AppointmentDAO.update(newAppointment);

                Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
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

        setValues();
    }

    private void setValues() {

        selectedAppointment = AppointmentsController.getSelectedAppointment();

        //Set text fields
        appointmentIdText.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleText.setText(selectedAppointment.getTitle());
        descriptionText.setText(selectedAppointment.getDescription());
        locationText.setText(selectedAppointment.getLocation());
        typeText.setText(selectedAppointment.getType());

        //Set start time spinner
        LocalTime startTime = selectedAppointment.getStartTime().toLocalTime();
        startSVF.setValue(startTime);
        startTimeSpinner.setValueFactory(startSVF);

        //Set end time spinner
        LocalTime endTime = selectedAppointment.getEndTime().toLocalTime();
        endSVF.setValue(endTime);
        endTimeSpinner.setValueFactory(endSVF);

        //Set start date picker
        LocalDate startDate = selectedAppointment.getStartTime().toLocalDate();
        startDatePicker.setValue(startDate);

        //Set end date picker
        LocalDate endDate = selectedAppointment.getEndTime().toLocalDate();
        endDatePicker.setValue(endDate);

        try {
            //Set contact combo box
            contactList = ContactDAO.selectAll();
            contactComboBox.setItems(contactList);
            selectedContact = ContactDAO.selectByID(selectedAppointment.getContactID());
            contactComboBox.setValue(selectedContact);

            //Set customer combo box
            customerList = CustomerDAO.selectAll();
            customerComboBox.setItems(customerList);
            selectedCustomer = CustomerDAO.selectByID(selectedAppointment.getCustomerID());
            customerComboBox.setValue(selectedCustomer);
            customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));

            //Set user combo box
            userList = UserDAO.selectAll();
            userComboBox.setItems(userList);
            selectedUser = UserDAO.selectByID(selectedAppointment.getUserID());
            userComboBox.setValue(selectedUser);
            userIdText.setText(String.valueOf(selectedUser.getUserID()));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private LocalDateTime getStartDateTime() {

        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        return startDateTime;
    }

    private LocalDateTime getEndDateTime() {

        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return endDateTime;
    }

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
