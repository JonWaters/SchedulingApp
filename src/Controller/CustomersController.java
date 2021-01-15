package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML
    private TableView<?> customersTable;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> postalColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> divisionColumn;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteCustomerButtonAction(ActionEvent event) {

    }

    @FXML
    void modifyCustomerButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void newCustomerButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/NewCustomer.fxml"));
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
