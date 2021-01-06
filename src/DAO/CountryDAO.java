package DAO;

import Model.Country;
import sample.Main;
import utils.DbQuery;

import java.sql.*;

public class CountryDAO {

    private final static Connection conn = Main.conn;

    public static void insert(Country country) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO Countries(Country, Create_Date, Created_By, Last_Update, " +
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
            e.getMessage()
        }
    }
}

