package DAO;

import Model.Country;
import Model.Division;
import sample.Main;
import utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

    private final static Connection conn = Main.conn;

    public static void create(Division division, Country country) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO first_level_divisions(Division, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) " +
                    "VALUES(?, NOW(), ?, NOW(), ?, ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, division.getDivisionName());
            ps.setString(2, division.getCreatedBy());
            ps.setString(3, division.getLastUpdatedBy());
            ps.setInt(4, country.getCountryID());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            division.setDivisionID(rs.getInt(1));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
