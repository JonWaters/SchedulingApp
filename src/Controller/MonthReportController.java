package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MonthReportController implements Initializable {

    @FXML
    private TableView<?> reportTable;

    @FXML
    private TableColumn<?, ?> yearColumn;

    @FXML
    private TableColumn<?, ?> monthColumn;

    @FXML
    private TableColumn<?, ?> totalColumn;

    @FXML
    private Label timestampLabel;

    @FXML
    void closeButtonAction(ActionEvent event) {

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
