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

public class ContactDAO {

    private final static Connection conn = Main.conn;

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
