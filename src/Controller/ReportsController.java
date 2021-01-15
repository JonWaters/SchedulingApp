package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TextArea reportsTextArea;

    @FXML
    private RadioButton typeRadioButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton scheduleRadioButton;

    @FXML
    private RadioButton locationRadioButton;

    @FXML
    void backButtonAction(ActionEvent event) {

    }

    @FXML
    void generateButtonAction(ActionEvent event) {

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
