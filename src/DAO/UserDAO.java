package DAO;

import Model.User;
import sample.Main;
import utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final static Connection conn = Main.conn;

    public static void create(User user) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO users(User_Name, Password, Create_Date, " +
                    "Created_By, Last_Update, Last_Updated_By) VALUES(?, ? NOW(), ?, NOW(), ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCreatedBy());
            ps.setString(4, user.getLastUpdatedBy());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            user.setUserID(rs.getInt(1));

            sqlStatement = "SELECT * FROM users WHERE User_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            ps = DbQuery.getPreparedStatement();

            ps.setInt(1, user.getUserID());

            ps.execute();

            rs = ps.getResultSet();
            rs.next();

            user.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            user.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User selectByID(int dbID) throws SQLException {

        User user = new User();

        try {
            String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            user.setUserID(rs.getInt("User_ID"));
            user.setUserName(rs.getString("User_Name"));
            user.setPassword(rs.getString("Password"));
            user.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            user.setCreatedBy(rs.getString("Created_By"));
            user.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }
}
