package Model;

import DAO.DivisionDAO;

import java.sql.SQLException;

public class CustomerDisplay extends Customer {

    String divisionName;

    public CustomerDisplay(Customer customer) throws SQLException {

        this.customerID = customer.customerID;
        this.customerName = customer.customerName;
        this.address = customer.address;
        this.postalCode = customer.postalCode;
        this.phone = customer.phone;
        this.divisionID = customer.divisionID;

        try {
            this.divisionName = DivisionDAO.selectByID(customer.divisionID).getDivisionName();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
