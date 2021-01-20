package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private User currentUser = LoginController.getCurrentUser();

    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private ObservableList<User> userList = FXCollections.observableArrayList();

    private Contact selectedContact;

    private Customer selectedCustomer;

    private User selectedUser;

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

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void customerComboBoxAction(ActionEvent event) {

        selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

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

        try {
            AppointmentDAO.create(newAppointment);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

        setDefaultValues();
    }

    private void setDefaultValues() {

        startSVF.setValue(LocalTime.of(8, 00));
        startTimeSpinner.setValueFactory(startSVF);
        endSVF.setValue(LocalTime.of(17, 00));
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

    private boolean fieldsNull(Appointment appointment) {

        boolean fieldsNull = false;

        if (appointment.getTitle() == null) {
            fieldsNull = true;
        } else if (appointment.getDescription() == null) {
            fieldsNull = true;
        } else if (appointment.getLocation() == null) {
            fieldsNull = true;
        } else if (appointment.getType() == null) {
            fieldsNull = true;
        } else if (appointment.getStartTime() == null) {
            fieldsNull = true;
        } else if (appointment.getEndTime() == null) {
            fieldsNull = true;
        } else if (appointment.getContactID() <= 0) {
            fieldsNull = true;
        } else if (appointment.getCustomerID() <= 0) {
            fieldsNull = true;
        } else if (appointment.getUserID() <= 0) {
            fieldsNull = true;
        }

        return fieldsNull;
    }

    private boolean startAfterEnd(Appointment appointment) {

        boolean startAfterEnd = false;
        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getEndTime();

        if (startTime.isAfter(endTime)) {
            startAfterEnd = true;
        }

        return startAfterEnd;
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
