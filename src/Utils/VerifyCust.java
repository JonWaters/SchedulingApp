package Utils;

import Model.Appointment;
import Model.Customer;
import javafx.scene.control.Alert;

/**
 * Utility class that validates a Customer.
 *
 * @author Jonathan Waters
 */
public class VerifyCust {

    /**
     * Validates if a customer has empty fields.
     *
     * @param customer The customer to be validated.
     * @return Boolean
     */
    public static boolean fieldsEmpty(Customer customer) {

        boolean fieldsEmpty = false;

        if (customer.getCustomerName().equals("")) {
            fieldsEmpty = true;
        } else if (customer.getAddress().equals("")) {
            fieldsEmpty = true;
        } else if (customer.getPostalCode().equals("")) {
            fieldsEmpty = true;
        } else if (customer.getPhone().equals("")) {
            fieldsEmpty = true;
        }

        if (fieldsEmpty) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("One or more text fields are empty.");
            alert.showAndWait();
        }

        return fieldsEmpty;
    }
}
