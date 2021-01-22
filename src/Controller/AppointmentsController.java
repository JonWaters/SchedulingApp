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
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    private static Appointment selectedAppointment;

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private ToggleGroup radioButtons;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton allRadioButton;

    public AppointmentsController() {
    }

    @FXML
    void allRadioButtonAction(ActionEvent event) {

        try {
            displayAllAppointments();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void customersButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) {

    }

    @FXML
    void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void modifyAppointmentButtonAction(ActionEvent event) throws IOException {

        selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("You must select an appointment from the list.");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyAppointment.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void monthRadioButtonAction(ActionEvent event) {

        try {
            displayAppointmentsByMonth();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void newAppointmentButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/NewAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void reportsButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

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

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }
}
