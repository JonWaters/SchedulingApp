package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private final static Connection conn = Main.conn;

    public static void create(Customer customer) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, " +
                    "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES(?, ?, ?, ? NOW(), ?, NOW(), ?, ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getCreatedBy());
            ps.setString(6, customer.getLastUpdatedBy());
            ps.setInt(7, customer.getDivisionID());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Customer selectByID(int dbID) throws SQLException {

        Customer customer = new Customer();

        try {
            String sqlStatement = "SELECT * FROM customers WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            customer.setCustomerID(rs.getInt("Customer_ID"));
            customer.setCustomerName(rs.getString("Customer_Name"));
            customer.setAddress(rs.getString("Address"));
            customer.setPostalCode(rs.getString("Postal_Code"));
            customer.setPhone(rs.getString("Phone"));
            customer.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            customer.setCreatedBy(rs.getString("Created_By"));
            customer.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            customer.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            customer.setDivisionID(rs.getInt("Division_ID"));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return customer;
    }

    public static ObservableList<Customer> selectAll() throws SQLException {

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * FROM customers";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("Customer_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customer.setAddress(rs.getString("Address"));
                customer.setPostalCode(rs.getString("Postal_Code"));
                customer.setPhone(rs.getString("Phone"));
                customer.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                customer.setCreatedBy(rs.getString("Created_By"));
                customer.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                customer.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                customer.setDivisionID(rs.getInt("Division_ID"));

                customers.add(customer);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public static void update(Customer customer) throws  SQLException {

        try {
            String sqlStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, " +
                    "Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, " +
                    "Division_ID = ? WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getLastUpdatedBy());
            ps.setInt(6, customer.getDivisionID());
            ps.setInt(7, customer.getCustomerID());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteByID(int dbID) throws SQLException {

        try {
            String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
