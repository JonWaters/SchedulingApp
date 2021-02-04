package Model;

import DAO.DivisionDAO;

import java.sql.SQLException;

/**
 * Wrapper class that extends Customer for displaying customers in the customers
 * tableview.
 */
public class CustomerDisplay extends Customer {

    /**
     * The division name.
     */
    String divisionName;

    /**
     * Argument based constructor.
     *
     * @param customer The Customer object to be wrapped.
     * @throws SQLException From DivisionDAO.selectByID().
     */
    public CustomerDisplay(Customer customer) throws SQLException {

        this.customerID = customer.customerID;
        this.customerName = customer.customerName;
        this.address = customer.address;
        this.postalCode = customer.postalCode;
        this.phone = customer.phone;
        this.createDate = customer.createDate;
        this.createdBy = customer.createdBy;
        this.lastUpdateTime = customer.lastUpdateTime;
        this.lastUpdatedBy = customer.lastUpdatedBy;
        this.divisionID = customer.divisionID;

        try {
            this.divisionName = DivisionDAO.selectByID(customer.divisionID).getDivisionName();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The getter for the division name.
     *
     * @return Division name String
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * The setter for the division name.
     *
     * @param divisionName Division name String.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
