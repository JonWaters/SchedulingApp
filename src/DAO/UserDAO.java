package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class that provides the interface for the User database table.
 *
 * @author Jonathan Waters
 */
public class UserDAO {

    /**
     * Database connection object obtained from Main
     */
    private final static Connection conn = Main.conn;

    /**
     * Inserts a new user record.
     *
     * @param user The user to be inserted.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
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
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects a user record by user ID.
     *
     * @param dbID The user ID.
     * @return A user object.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
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

    /**
     * Selects a user record by user name.
     *
     * @param userName The user name string.
     * @return A user object.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static User selectByName(String userName) throws SQLException {

        User user = new User();

        try {
            String sqlStatement = "SELECT * FROM users WHERE User_Name = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, userName);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {

                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    /**
     * Selects all user records.
     *
     * @return A list of user objects.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static ObservableList<User> selectAll() throws SQLException {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * FROM users";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                User user = new User();

                user.setUserID(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));

                users.add(user);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    /**
     * Updates a user record.
     *
     * @param user The user object to be updated.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void update(User user) throws SQLException {

        try {
            String sqlStatement = "UPDATE users SET User_Name = ?, Password = ?, Last_Update = NOW(), " +
                    "Last_Updated_By = ? WHERE User_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getLastUpdatedBy());
            ps.setInt(4, user.getUserID());

            ps.execute();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a user record by user ID.
     *
     * @param dbID The user ID.
     * @throws SQLException From DbQuery.setPreparedStatement().
     */
    public static void deleteByID(int dbID) throws SQLException {

        try {
            String sqlStatement = "DELETE FROM users WHERE User_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
