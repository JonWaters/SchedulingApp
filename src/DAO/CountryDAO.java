package DAO;

import Model.Country;
import sample.Main;
import utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CountryDAO {

    private final static Connection conn = Main.conn;

    public static Country insert(Country country) throws SQLException {

        String sqlStatement = "INSERT INTO Countries(Country, Create_Date, Created_By, Last_Update, " +
                "Last_Updated_by) VALUES(?, ?, ?, ?, ?)";

        DbQuery.setPreparedStatement(conn, sqlStatement);

        PreparedStatement ps = DbQuery.getPreparedStatement();

        ps.setString(1, country.getCountryName());
        ps.setTimestamp(2, Timestamp.valueOf(country.getCreateDate()));

        return country;
    }
}

