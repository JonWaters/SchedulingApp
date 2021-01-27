package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.TypeReportDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TypeReportController implements Initializable {

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @FXML
    private TableView<TypeReportDisplay> reportTable;

    @FXML
    private TableColumn<TypeReportDisplay, String> typeColumn;

    @FXML
    private TableColumn<TypeReportDisplay, Integer> totalColumn;

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

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        timestampLabel.setText(LocalDateTime.now().format(dateTimeFormat));

        generateReport();
    }

    private void generateReport() {

        try{
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
            ObservableList<TypeReportDisplay> typeReportList = FXCollections.observableArrayList();

            //Get unique years
            for (Appointment appointment : dbAppointments) {
                String type = appointment.getType();

                if (appointmentTypes.contains(type)) {
                    continue;
                } else {
                    appointmentTypes.add(type);
                }
            }

            String typeName;
            int typeCount;

            for (int i = 0; i < appointmentTypes.size(); i++) {
                typeName = appointmentTypes.get(i);
                typeCount = 0;
                for (int k = 0; k < dbAppointments.size(); k++) {
                    if (dbAppointments.get(k).getType().equals(appointmentTypes.get(i))) {
                        typeCount++;
                    }
                }
                TypeReportDisplay typeDisplay = new TypeReportDisplay(typeName, typeCount);
                typeReportList.add(typeDisplay);
            }

            reportTable.setItems(typeReportList);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
