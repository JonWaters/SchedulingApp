package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.AppointmentDisplay;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides control logic for the Appointments screen.
 *
 * @author Jonathan Waters
 */
public class AppointmentsController implements Initializable {

    /**
     * The appointment object selected in the appointments table.
     */
    private static Appointment selectedAppointment;

    /**
     * The appointments table.
     */
    @FXML
    private TableView<Appointment> appointmentsTable;

    /**
     * The appointment ID column.
     */
    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    /**
     * The Title column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    /**
     * The Description column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    /**
     * The Location column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    /**
     * The Contact column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> contactColumn;

    /**
     * The Type column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /**
     * The start time column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> startColumn;

    /**
     * The end time column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> endColumn;

    /**
     * The Customer ID column for the appointments table.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    /**
     * The Week radio button.
     */
    @FXML
    private RadioButton weekRadioButton;

    /**
     * The toggle group for the radio buttons.
     */
    @FXML
    private ToggleGroup radioButtons;

    /**
     * The Month radio button.
     */
    @FXML
    private RadioButton monthRadioButton;

    /**
     * The All radio button.
     */
    @FXML
    private RadioButton allRadioButton;

    @FXML
    private Button clearSearchButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    /**
     * The constructor for the AppointmentsController class.
     */
    public AppointmentsController() {
    }

    @FXML
    void clearSearchButtonAction(ActionEvent event) {

    }

    /**
     * Calls the displayAllAppointments() method.
     *
     * @param event All radio button action.
     */
    @FXML
    void allRadioButtonAction(ActionEvent event) {

        try {
            displayAllAppointments();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads CustomersController.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void customersButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays confirmation message end deletes selected appointment.
     *
     * The appointment list is refreshed depending on the radio button selected.
     *
     * @param event Delete appointment button action.
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) {

        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("You must select an appointment from the list.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                try {
                    AppointmentDAO.deleteByID(selectedAppointment.getAppointmentID());
                }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);

                info.setTitle("Information");
                info.setHeaderText("Appointment ID " + selectedAppointment.getAppointmentID() +
                        " of type " + selectedAppointment.getType() + " has been cancelled.");
                info.showAndWait();

                if (allRadioButton.isSelected()) {
                    try {
                        displayAllAppointments();
                    }
                    catch(SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (weekRadioButton.isSelected()) {
                    try {
                        displayAppointmentsByWeek();
                    }
                    catch(SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (monthRadioButton.isSelected()) {
                    try {
                        displayAppointmentsByMonth();
                    }
                    catch(SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Closes and terminates application.
     *
     * @param event Exit button action.
     */
    @FXML
    void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }

    /**
     * Loads ModifyAppointmentController.
     *
     * @param event Modify appointment button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void modifyAppointmentButtonAction(ActionEvent event) throws IOException {

        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("You must select an appointment from the list.");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/View/ModifyAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Calls the displayAppointmentsByMonth() method.
     * @param event Month radio button action.
     */
    @FXML
    void monthRadioButtonAction(ActionEvent event) {

        try {
            displayAppointmentsByMonth();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads NewAppointmentController.
     *
     * @param event New appointment button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void newAppointmentButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/View/NewAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ReportsController.
     *
     * @param event Reports button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void reportsButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void searchButtonAction(ActionEvent event) {

    }

    /**
     * Calls the displayAppointmentsByWeek() method.
     * @param event Week radio button action.
     */
    @FXML
    void weekRadioButtonAction(ActionEvent event) {

        try {
            displayAppointmentsByWeek();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        try {
            displayAllAppointments();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        allRadioButton.setSelected(true);
    }

    /**
     * Sets the appointments table to show All appointments.
     *
     * @throws SQLException From AppointmentDAO.selectAll().
     */
    private void displayAllAppointments() throws SQLException {

        try {
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();

            for (Appointment appointment : dbAppointments) {

                AppointmentDisplay newAppointment = new AppointmentDisplay(appointment);
                appointments.add(newAppointment);
            }

            appointmentsTable.setItems(appointments);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Sets the appointments table to show appointments with a start time in
     * the next seven days.
     *
     * @throws SQLException From AppointmentDAO.selectAll().
     */
    private void displayAppointmentsByWeek() throws SQLException {

        try {
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();

            LocalDate todayDate = LocalDate.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime todayMidnight = LocalDateTime.of(todayDate, midnight);
            LocalDateTime oneWeek = todayMidnight.plusWeeks(1);

            for (Appointment appointment : dbAppointments) {

                if (appointment.getStartTime().isAfter(todayMidnight) &&
                        appointment.getStartTime().isBefore(oneWeek)) {
                    AppointmentDisplay newAppointment = new AppointmentDisplay(appointment);
                    appointments.add(newAppointment);
                }
            }

            appointmentsTable.setItems(appointments);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the appointments table to show appointments with a start time in the
     * next thirty days.
     *
     * @throws SQLException From AppointmentDAO.selectAll().
     */
    private void displayAppointmentsByMonth() throws SQLException {

        try {
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();

            LocalDate todayDate = LocalDate.now();
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime todayMidnight = LocalDateTime.of(todayDate, midnight);
            LocalDateTime oneMonth = todayMidnight.plusMonths(1);

            for (Appointment appointment : dbAppointments) {

                if (appointment.getStartTime().isAfter(todayMidnight) &&
                        appointment.getStartTime().isBefore(oneMonth)) {
                    AppointmentDisplay newAppointment = new AppointmentDisplay(appointment);
                    appointments.add(newAppointment);
                }
            }

            appointmentsTable.setItems(appointments);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Passes selected appointment object to other controllers.
     *
     * @return selectedAppointment object.
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }
}
