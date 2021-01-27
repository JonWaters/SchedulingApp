package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.MonthTypeReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

public class MonthTypeReportController implements Initializable {

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @FXML
    private TableView<MonthTypeReport> reportTable;

    @FXML
    private TableColumn<MonthTypeReport, Integer> yearColumn;

    @FXML
    private TableColumn<MonthTypeReport, Month> monthColumn;

    @FXML
    private TableColumn<MonthTypeReport, String> typeColumn;

    @FXML
    private TableColumn<MonthTypeReport, Integer> totalColumn;

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

        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        timestampLabel.setText(LocalDateTime.now().format(dateTimeFormat));

        generateReport();
    }

    private void generateReport() {

        try {
            ObservableList<Integer> years = FXCollections.observableArrayList();
            ObservableList<Month> months = FXCollections.observableArrayList();
            ObservableList<String> types = FXCollections.observableArrayList();
            ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();
            ObservableList<MonthTypeReport> reportList = FXCollections.observableArrayList();

            //Get unique years
            for (Appointment appointment : dbAppointments) {
                Integer year = appointment.getStartTime().getYear();

                if (years.contains(year)) {
                    continue;
                } else {
                    years.add(year);
                }
            }

            //Get unique months
            for (Appointment appointment : dbAppointments) {
                Month month = appointment.getStartTime().getMonth();

                if (months.contains(month)) {
                    continue;
                } else {
                    months.add(month);
                }
            }

            //Get unique types
            for (Appointment appointment : dbAppointments) {
                String type = appointment.getType();

                if (types.contains(type)) {
                    continue;
                } else {
                    types.add(type);
                }
            }

            Integer year;
            Month month;
            String type;
            Appointment appointment;
            int typeCount;

            //Loop through years
            for (int i = 0; i < years.size(); i++) {
                year = years.get(i);

                //Loop through months
                for (int j = 0; j < months.size(); j++) {
                    month = months.get(j);

                    //Loop through types
                    for (int k = 0; k < types.size(); k++) {
                        type = types.get(k);
                        typeCount = 0;

                        //Loop through all appointments
                        for (int l = 0; l < dbAppointments.size(); l++) {
                            appointment = dbAppointments.get(l);

                            //If appointment is same year, month, and type - increment count.
                            if (appointment.getStartTime().getYear() == year &&
                            appointment.getStartTime().getMonth().equals(month) &&
                            appointment.getType().equals(type)) {

                                typeCount++;
                            }

                        }

                        //If count is greater than 0:
                        //Build report display object and add to list to be displayed
                        if (typeCount > 0) {
                            MonthTypeReport reportObject = new MonthTypeReport(year, month, type, typeCount);
                            reportList.add(reportObject);
                        }
                    }
                }
            }

            //Set table list
            reportTable.setItems(reportList);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
