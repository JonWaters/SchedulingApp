package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class that provides the interface for the first_level_divisions database table.
 *
 * @author Jonathan Waters
 */
public class DivisionDAO {

    /**
     * Database connection object obtained from Main.
     */
    private final static Connection conn = Main.conn;

    /**
     * Inserts a new division record
     *
     * @param division The division object to be inserted.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void create(Division division) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO first_level_divisions(Division, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) " +
                    "VALUES(?, NOW(), ?, NOW(), ?, ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, division.getDivisionName());
            ps.setString(2, division.getCreatedBy());
            ps.setString(3, division.getLastUpdatedBy());
            ps.setInt(4, division.getCountryID());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects a division record by division ID.
     *
     * @param dbID The division ID.
     * @return A division object.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
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

    /**
     * Selects all division records.
     *
     * @return A list of division objects.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static ObservableList<Division> selectAll() throws SQLException {

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try{
            String sqlStatement = "SELECT * FROM first_level_divisions";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {

                Division division = new Division();

                division.setDivisionID(rs.getInt("Division_ID"));
                division.setDivisionName(rs.getString("Division"));
                division.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                division.setCreatedBy(rs.getString("Created_By"));
                division.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                division.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                division.setCountryID(rs.getInt("COUNTRY_ID"));

                divisions.add(division);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return divisions;
    }

    /**
     * Updates a division record.
     *
     * @param division The division object to be updated
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void update(Division division) throws SQLException {

        try{
            String sqlStatement = "UPDATE first_level_divisions SET Division = ?, Last_Update = NOW(), " +
                    "Last_Updated_By = ?, COUNTRY_ID = ? WHERE Division_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, division.getDivisionName());
            ps.setString(2, division.getLastUpdatedBy());
            ps.setInt(3, division.getCountryID());
            ps.setInt(4, division.getDivisionID());

            ps.execute();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a division record by division ID.
     *
     * @param dbID The division ID.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void deleteByID(int dbID) throws SQLException {

        try{
            String sqlStatement = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

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
