package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import Model.Country;
import Model.Customer;
import Model.Division;
import Model.User;
import Utils.VerifyCust;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides the control logic for the Modify Customer screen.
 *
 * @author Jonathan Waters
 */
public class ModifyCustomerController implements Initializable {

    /**
     * List of country objects for the country combo box.
     */
    private ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * The currently logged in user obtained from LoginController.
     */
    private User currentUser = LoginController.getCurrentUser();

    /**
     * The customer object selected in CustomerController.
     */
    private Customer selectedCustomer;

    /**
     * The customer ID text field
     */
    @FXML
    private TextField customerIdText;

    /**
     * The customer name text field.
     */
    @FXML
    private TextField nameText;

    /**
     * The customer address text field.
     */
    @FXML
    private TextField addressText;

    /**
     * The customer postal code text field.
     */
    @FXML
    private TextField postalText;

    /**
     * The customer phone number text field.
     */
    @FXML
    private TextField phoneText;

    /**
     * The country combo box.
     */
    @FXML
    private ComboBox<Country> countryComboBox;

    /**
     * The division combo box.
     */
    @FXML
    private ComboBox<Division> divisionComboBox;

    /**
     * Displays confirmation message and loads CustomerController.
     *
     * @param event Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the Customers screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Sets value for country combo box and filters division combo box based on country value.
     *
     * @param event Country combo box action.
     */
    @FXML
    void countryComboBoxAction(ActionEvent event) {

        try {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Division> dbDivisions = DivisionDAO.selectAll();
            ObservableList<Division> divisionByCountry = FXCollections.observableArrayList();

            for (Division division : dbDivisions) {

                if (division.getCountryID() == selectedCountry.getCountryID()) {
                    divisionByCountry.add(division);
                }
            }

            divisionComboBox.setItems(divisionByCountry);
            divisionComboBox.setValue(null);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates new customer object with database ID from selected customer and updates database.
     *
     * @param event Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    void saveButtonAction(ActionEvent event) throws IOException {

        try {
            String customerName = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalText.getText();
            String phone = phoneText.getText();
            String createdBy = selectedCustomer.getCreatedBy();
            String lastUpdatedBy = currentUser.getUserName();
            int divisionID = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();

            Customer newCustomer = new Customer(customerName, address, postalCode, phone, createdBy,
                    lastUpdatedBy, divisionID);

            newCustomer.setCustomerID(selectedCustomer.getCustomerID());

            if (!VerifyCust.fieldsEmpty(newCustomer)) {

                CustomerDAO.update(newCustomer);

                Parent parent = FXMLLoader.load(getClass().getResource("../View/Customers.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
        catch(NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("One or more fields are not selected.");
            alert.showAndWait();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
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

        setValues();
    }

    /**
     * Sets field values based on customer object selected in CustomersController.
     */
    private void setValues() {

        selectedCustomer = CustomersController.getSelectedCustomer();

        //Set text fields
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameText.setText(selectedCustomer.getCustomerName());
        addressText.setText(selectedCustomer.getAddress());
        postalText.setText(selectedCustomer.getPostalCode());
        phoneText.setText(selectedCustomer.getPhone());

        try {
            //Get selected division then country
            Division selectedDivision = DivisionDAO.selectByID(selectedCustomer.getDivisionID());
            Country country = CountryDAO.selectByID(selectedDivision.getCountryID());

            //Set country list
            countryList = CountryDAO.selectAll();
            countryComboBox.setItems(countryList);
            countryComboBox.setValue(country);

            //Set division list
            ObservableList<Division> dbDivisions = DivisionDAO.selectAll();
            ObservableList<Division> divisionByCountry = FXCollections.observableArrayList();

            for (Division division : dbDivisions) {

                if (division.getCountryID() == country.getCountryID()) {
                    divisionByCountry.add(division);
                }
            }

            divisionComboBox.setItems(divisionByCountry);
            divisionComboBox.setValue(selectedDivision);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
