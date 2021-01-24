package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    private static Customer selectedCustomer;

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
    void deleteCustomerButtonAction(ActionEvent event) throws SQLException {

        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("You must select a customer from the list.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected customer?\n" +
                    "Deleting a customer will delete ALL associated appointments!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                try {
                    ObservableList<Appointment> dbAppointments = AppointmentDAO.selectAll();

                    for (Appointment appointment : dbAppointments) {

                        if (appointment.getCustomerID() == selectedCustomer.getCustomerID()) {
                            AppointmentDAO.deleteByID(appointment.getAppointmentID());
                        }
                    }

                    CustomerDAO.deleteByID(selectedCustomer.getCustomerID());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);

                info.setTitle("Information");
                info.setHeaderText("Customer " + selectedCustomer.getCustomerName() +
                        " and all associated appointments have been deleted");
                info.showAndWait();

                displayAllCustomers();
            }
        }
    }

    @FXML
    void modifyCustomerButtonAction(ActionEvent event) throws IOException {

        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("You must select a customer from the list.");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyCustomer.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
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

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}
