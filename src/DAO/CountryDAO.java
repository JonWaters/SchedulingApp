package DAO;

import Model.Country;
import sample.Main;
import utils.DbQuery;

import java.sql.*;

public class CountryDAO {

    private final static Connection conn = Main.conn;

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

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            country.setCountryID(rs.getInt(1));
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

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
}

