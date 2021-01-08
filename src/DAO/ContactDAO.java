package DAO;

import Model.Contact;
import sample.Main;
import utils.DbQuery;

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

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            contact.setContactID(rs.getInt(1));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
