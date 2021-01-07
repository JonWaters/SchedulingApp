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

            sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            ps = DbQuery.getPreparedStatement();

            ps.setInt(1, division.getDivisionID());

            ps.execute();

            rs = ps.getResultSet();
            rs.next();

            division.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            division.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Division selectByID(int dbID) throws SQLException {

        Division division = new Division();

        try {
            String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            division.setDivisionID(rs.getInt("Division_ID"));
            division.setDivisionName(rs.getString("Division"));
            division.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            division.setCreatedBy(rs.getString("Created_By"));
            division.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            division.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            division.setCountryID(rs.getInt("COUNTRY_ID"));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return division;
    }
}
