package DAO;

import Model.Customer;
import sample.Main;
import utils.DbQuery;

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

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            customer.setCustomerID(rs.getInt(1));

            sqlStatement = "SELECT * FROM customers WHERE Customer_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            ps = DbQuery.getPreparedStatement();

            ps.setInt(1, customer.getCustomerID());

            ps.execute();

            rs = ps.getResultSet();
            rs.next();

            customer.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            customer.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
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
}
