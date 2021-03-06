package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.*;

/**
 * Data Access Object (DAO) class that provides the interface for the Country database table.
 *
 * @author Jonathan Waters
 */
public class CountryDAO {

    /**
     * Database connection object obtained from Main.
     */
    private final static Connection conn = Main.conn;

    /**
     * Inserts a new recorded into the country table.
     *
     * @param country Country object to be inserted.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void create(Country country) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Update, " +
                    "Last_Updated_by) VALUES(?, NOW(), ?, NOW(), ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, country.getCountryName());
            ps.setString(2, country.getCreatedBy());
            ps.setString(3, country.getLastUpdatedBy());

            ps.execute();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects a country record by country ID.
     *
     * @param dbID The country ID.
     * @return A country object.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static Country selectByID(int dbID) throws SQLException {

        Country country = new Country();

        try{
            String sqlStatement = "SELECT * FROM countries WHERE Country_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            country.setCountryID(rs.getInt("Country_ID"));
            country.setCountryName(rs.getString("Country"));
            country.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            country.setCreatedBy(rs.getString("Created_By"));
            country.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            country.setLastUpdatedBy(rs.getString("Last_Updated_By"));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return country;
    }

    /**
     * Selects all country records.
     *
     * @return A list of country objects.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static ObservableList<Country> selectAll() throws SQLException {

        ObservableList<Country> countries = FXCollections.observableArrayList();

        try{
            String sqlStatement = "SELECT * FROM countries";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {

                Country country = new Country();

                country.setCountryID(rs.getInt("Country_ID"));
                country.setCountryName(rs.getString("Country"));
                country.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                country.setCreatedBy(rs.getString("Created_By"));
                country.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                country.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                countries.add(country);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return countries;
    }

    /**
     * Updates a country record.
     *
     * @param country Country object to be updated.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void update(Country country) throws SQLException {

        try{
            String sqlStatement = "UPDATE countries SET Country = ?, Last_Update = NOW(), " +
                    "Last_Updated_By = ? WHERE Country_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, country.getCountryName());
            ps.setString(2, country.getLastUpdatedBy());
            ps.setInt(3, country.getCountryID());

            ps.execute();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a country record by country ID.
     *
     * @param dbID The country ID
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void deleteByID(int dbID) throws SQLException {

        try{
            String sqlStatement = "DELETE FROM countries WHERE Country_ID = ?";

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

