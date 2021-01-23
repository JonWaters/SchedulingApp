package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.AppointmentDisplay;
import Model.Customer;
import Model.CustomerDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> postalColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> divisionColumn;

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

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        try {
            displayAllCustomers();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAllCustomers() throws SQLException {

        try {
            ObservableList<Customer> dbCustomers = CustomerDAO.selectAll();
            ObservableList<Customer> customers = FXCollections.observableArrayList();

            for (Customer customer : dbCustomers) {

                CustomerDisplay newCustomer = new CustomerDisplay(customer);
                customers.add(newCustomer);
            }

            customersTable.setItems(customers);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
