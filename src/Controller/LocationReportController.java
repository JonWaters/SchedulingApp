package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.AppointmentDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller class that provides the control logic for the Location report screen.
 *
 * @author Jonthan Waters
 */
public class LocationReportController implements Initializable {

    /**
     * The date time format.
     */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    /**
     * The report table.
     */
    @FXML
    private TableView<Appointment> reportTable;

    /**
     * The appointment ID column for the report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    /**
     * The Title column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    /**
     * The Type column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /**
     * The Location column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    /**
     * The Contact column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> contactColumn;

    /**
     * The start time column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> startColumn;

    /**
     * The end time column for the report table.
     */
    @FXML
    private TableColumn<Appointment, String> endColumn;

    /**
     * The customer ID column for the report table.
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    /**
     * The timestamp label.
     */
    @FXML
    private Label timestampLabel;

    @FXML
    void closeButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        timestampLabel.setText(LocalDateTime.now().format(dateTimeFormat));

        generateReport();
    }

    private void generateReport() {

        try {
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAllOrderByLocation();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();

            for (Appointment appointment : dbAppointments) {

                AppointmentDisplay newAppointment = new AppointmentDisplay(appointment);
                appointments.add(newAppointment);
            }

            reportTable.setItems(appointments);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
