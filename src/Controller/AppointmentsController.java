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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    @FXML
    private TableView<AppointmentDisplay> appointmentsTable;

    @FXML
    private TableColumn<AppointmentDisplay, Integer> idColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> titleColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> descriptionColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> locationColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> contactColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> typeColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> startColumn;

    @FXML
    private TableColumn<AppointmentDisplay, String> endColumn;

    @FXML
    private TableColumn<AppointmentDisplay, Integer> customerIdColumn;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton monthRadioButton;

    public AppointmentsController() {
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

        Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void monthRadioButtonAction(ActionEvent event) {

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
            displayAppointmentsByWeek();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void displayAppointmentsByWeek() throws SQLException {

        try {
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<AppointmentDisplay> appointments = FXCollections.observableArrayList();

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime oneWeek = today.plusDays(6);

            for (Appointment appointment : dbAppointments) {

                if (appointment.getStartTime().isAfter(today) &&
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
}
