package Controller;

import DAO.CountryDAO;
import DAO.DivisionDAO;
import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    ObservableList<Country> countryList = FXCollections.observableArrayList();

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalText;

    @FXML
    private TextField phoneText;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<Division> divisionComboBox;

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
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
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

        setDefaultValues();
    }

    private void setDefaultValues() {

        try {
            countryList = CountryDAO.selectAll();
            countryComboBox.setItems(countryList);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
