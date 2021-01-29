package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import Model.CustomerDisplay;
import javafx.beans.property.SimpleStringProperty;
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

/**
 * Controller class that provides control logic for the Customers screen. Contains
 * a method with an example of Lambda use.
 */
public class CustomersController implements Initializable {

    /**
     * The customer object selected in the customers table.
     */
    private static Customer selectedCustomer;

    /**
     * The Customers table.
     */
    @FXML
    private TableView<CustomerDisplay> customersTable;

    /**
     * The Name column for the customers table.
     */
    @FXML
    private TableColumn<CustomerDisplay, String> nameColumn;

    /**
     * The Address column for the customers table.
     */
    @FXML
    private TableColumn<CustomerDisplay, String> addressColumn;

    /**
     * The Postal Code column for the customers table.
     */
    @FXML
    private TableColumn<CustomerDisplay, String> postalColumn;

    /**
     * The Phone Number column for the customers table.
     */
    @FXML
    private TableColumn<CustomerDisplay, String> phoneColumn;

    /**
     * The Division Name column for the customers table.
     */
    @FXML
    private TableColumn<CustomerDisplay, String> divisionColumn;

    /**
     * Loads the AppointmentsController.
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
     * Displays confirmation message and deletes the selected customer.
     *
     * Also deletes All appointments associated with selected customer.
     *
     * @param event Delete customer button action.
     * @throws SQLException From AppointmentDAO.selectAll(), AppointmentDAO.deleteByID(),
     * and CustomerDAO.deleteByID().
     */
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

    /**
     * Loads ModifyCustomerController.
     *
     * @param event Modify customer button action.
     * @throws IOException From FXMLLoader.
     */
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

    /**
     * Loads NewCustomerController.
     *
     * @param event New customer button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void newCustomerButtonAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../View/NewCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Contains example #1 of lambda expression. Called to initialize a controller
     * after its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Use of Lambdas to set cell values
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        postalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        divisionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivisionName()));

        try {
            displayAllCustomers();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets customer table to display All customers.
     *
     * Wrapper class used for customer object display.
     *
     * @throws SQLException From CustomerDAO.selectAll().
     */
    private void displayAllCustomers() throws SQLException {

        try {
            ObservableList<Customer> dbCustomers = CustomerDAO.selectAll();
            ObservableList<CustomerDisplay> customers = FXCollections.observableArrayList();

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

    /**
     * Passes selected customer object to other controllers.
     * @return
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}
