package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class that provides the interface for the Contacts database table.
 *
 * @author Jonathan Waters
 */
public class ContactDAO {

    /**
     * Database connection object obtained from Main.
     */
    private final static Connection conn = Main.conn;

    /**
     * Inserts new record into the contacts table using values from a contact object.
     *
     * @param contact The contact object to be inserted.
     * @throws SQLException From DbQuery.setPreparedStatement.
     */
    public static void create(Contact contact) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO contacts(Contact_Name, Email) VALUES(?, ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getEmail());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects a record from the contacts table by contact ID.
     *
     * @param dbID The contact ID.
     * @return A contact object.
     * @throws SQLException From DbQuery.setPreparedStatement.
     */
    public static Contact selectByID(int dbID) throws SQLException {

        Contact contact = new Contact();

        try {
            String sqlStatement = "SELECT * FROM contacts WHERE Contact_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            contact.setContactID(rs.getInt("Contact_ID"));
            contact.setContactName(rs.getString("Contact_Name"));
            contact.setEmail(rs.getString("Email"));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return contact;
    }

    /**
     * Selects all records from the contacts table.
     *
     * @return A list of contact objects.
     * @throws SQLException From DbQuery.setPreparedStatement.
     */
    public static ObservableList<Contact> selectAll() throws SQLException {

        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * FROM contacts";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                Contact contact = new Contact();

                contact.setContactID(rs.getInt("Contact_ID"));
                contact.setContactName(rs.getString("Contact_Name"));
                contact.setEmail(rs.getString("Email"));

                contacts.add(contact);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return contacts;
    }

    /**
     * Updates a record in the contacts table.
     *
     * @param contact Contact object to be updated.
     * @throws SQLException From DbQuery.setPreparedStatement.
     */
    public static void update(Contact contact) throws SQLException {

        try {
            String sqlStatement = "UPDATE contacts SET Contact_Name = ?, Email = ? " +
                    "WHERE Contact_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getEmail());
            ps.setInt(3, contact.getContactID());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a record from the contacts table by contact ID.
     *
     * @param dbID The contact ID to be deleted.
     * @throws SQLException From DbQuery.setPreparedStatement.
     */
    public static void deleteByID(int dbID) throws SQLException {

        try {
            String sqlStatement = "DELETE FROM contacts WHERE Contact_ID = ?";

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
