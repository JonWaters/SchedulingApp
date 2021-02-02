package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that provides the control logic for the Reports screen.
 *
 * @author Jonathan Waters
 */
public class ReportsController implements Initializable {

    /**
     * The appointment type report radio button.
     */
    @FXML
    private RadioButton typeRadioButton;

    /**
     * The radio button toggle group.
     */
    @FXML
    private ToggleGroup radioButtons;

    /**
     * The appointments by month report radio button.
     */
    @FXML
    private RadioButton monthRadioButton;

    /**
     * The contact schedule report radio button.
     */
    @FXML
    private RadioButton scheduleRadioButton;

    /**
     * The appointments by location report radio button.
     */
    @FXML
    private RadioButton locationRadioButton;

    /**
     * Loads AppointmentsController.
     *
     * @param event Back button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void backButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads controller based on radio button selected.
     *
     * @param event Generate button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void generateButtonAction(ActionEvent event) throws IOException {

        if (monthRadioButton.isSelected()) {

            Parent parent = FXMLLoader.load(getClass().getResource("../View/MonthTypeReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (scheduleRadioButton.isSelected()) {

            Parent parent = FXMLLoader.load(getClass().getResource("../View/ScheduleReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (locationRadioButton.isSelected()) {

            Parent parent = FXMLLoader.load(getClass().getResource("../View/LocationReport.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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

    }
}
