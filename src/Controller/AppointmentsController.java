package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    @FXML
    private TableView<?> appointmentsTable;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    void customersButttonAction(ActionEvent event) {

    }

    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) {

    }

    @FXML
    void exitButtonAction(ActionEvent event) {

    }

    @FXML
    void modifyAppointmentButtonAction(ActionEvent event) {

    }

    @FXML
    void monthRadioButtonAction(ActionEvent event) {

    }

    @FXML
    void newAppoinmentButtonAction(ActionEvent event) {

    }

    @FXML
    void reportsButtonAction(ActionEvent event) {

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

    }
}
