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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class NewAppointmentController implements Initializable {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm");

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

            if (!fieldsEmpty(newAppointment) &&
                    !startAfterEnd(newAppointment) &&
                    !overlappingAppointment(newAppointment) &&
                    !outsideBusinessHours(newAppointment)) {

                AppointmentDAO.create(newAppointment);

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

        setDefaultValues();
    }

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

    private boolean fieldsEmpty(Appointment appointment) {

        boolean fieldsEmpty = false;

        if (appointment.getTitle().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getDescription().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getLocation().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getType().equals("")) {
            fieldsEmpty = true;
        }

        if (fieldsEmpty) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("One or more text fields are empty.");
            alert.showAndWait();
        }

        return fieldsEmpty;
    }

    private boolean startAfterEnd(Appointment appointment) {

        boolean startAfterEnd = false;
        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getEndTime();

        if (startTime.isAfter(endTime)) {
            startAfterEnd = true;
        }

        if (startAfterEnd) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The start time cannot occur after the end time.");
            alert.showAndWait();
        }

        return startAfterEnd;
    }

    private boolean outsideBusinessHours(Appointment appointment) {

        //Build zoned appointment start and rezone as eastern time.
        LocalDateTime startDateTime = appointment.getStartTime();
        LocalDate startDate = startDateTime.toLocalDate();
        ZonedDateTime startTimeZoned = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime startTimeAsEST = startTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Build zoned business start time and zone as eastern time.
        LocalTime businessStartTime = LocalTime.parse("08:00:00");
        LocalDateTime businessStartDateTime = LocalDateTime.of(startDate, businessStartTime);
        ZonedDateTime businessStartTimeZoned = businessStartDateTime.atZone(ZoneId.of("America/New_York"));

        //Build zoned appointment end and rezone as eastern time.
        LocalDateTime endDateTime = appointment.getEndTime();
        ZonedDateTime endTimeZoned = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime endTimeAsEST = endTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Build zoned business end time and zone as eastern time.
        //startDate is reused for business end because appointment cannot span multiple days.
        LocalTime businessEndTime = LocalTime.parse("22:00:00");
        LocalDateTime businessEndDateTime = LocalDateTime.of(startDate, businessEndTime);
        ZonedDateTime businessEndTimeZoned = businessEndDateTime.atZone(ZoneId.of("America/New_York"));

        boolean outsideBusinessHours = false;

        if (startTimeAsEST.isBefore(businessStartTimeZoned) ||
                endTimeAsEST.isAfter(businessEndTimeZoned)) {

            outsideBusinessHours = true;
        }

        if (outsideBusinessHours) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The appointment you trying to create occurs outside of business hours.\n" +
                                "Business hours are from 08:00 to 22:00 EST.");
            alert.showAndWait();
        }

        return outsideBusinessHours;
    }

    private boolean overlappingAppointment(Appointment newAppointment) throws SQLException {

        ObservableList<Appointment> allAppointments = AppointmentDAO.selectAll();

        boolean overlapping = false;

        Appointment overlappingAppointment = new Appointment();

        for (Appointment appointment : allAppointments) {

            int newApptCustID = newAppointment.getCustomerID();
            LocalDateTime newApptStart = newAppointment.getStartTime();
            LocalDateTime newApptEnd = newAppointment.getEndTime();

            int apptCustID = appointment.getCustomerID();
            LocalDateTime apptStart = appointment.getStartTime();
            LocalDateTime apptEnd = appointment.getEndTime();

            overlappingAppointment = appointment;

            if ((newApptCustID == apptCustID) && newApptStart.isAfter(apptStart) &&
                newApptStart.isBefore(apptEnd)) {
                overlapping = true;
                break;
            } else if ((newApptCustID == apptCustID) && newApptEnd.isAfter(apptStart) &&
                        newApptEnd.isBefore(apptEnd)) {
                overlapping = true;
                break;
            } else if ((newApptCustID == apptCustID) && newApptStart.isBefore(apptStart) &&
                        newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;
            }
        }

        if (overlapping) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The appointment you are trying to create overlaps with " +
                    "another appointment starting at " + overlappingAppointment.getStartTime().format(dateTimeFormat) +
                    " and ending at " + overlappingAppointment.getEndTime().format(dateTimeFormat) + ".");
            alert.showAndWait();
        }

        return overlapping;
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
