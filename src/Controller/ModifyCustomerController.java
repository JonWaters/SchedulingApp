package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalText;

    @FXML
    private TextField phoneText;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<?> divisionComboBox;

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void countryComboBoxAction(ActionEvent event) {

    }

    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
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

    }
}
