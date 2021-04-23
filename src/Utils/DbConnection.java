package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class that creates a database connection.
 *
 * @author Jonathan Waters
 */
public class DbConnection {

    // URL parts
    /**
     * The protocol for the database.
     */
    private static final String protocol = "jdbc";
    /**
     * The database vendor.
     */
    private static final String vendorName = ":mysql:";
    /**
     * The database server location.
     */
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07NQ3";

    // URL
    /**
     * The concatenated database URL string.
     */
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver interface reference
    /**
     * The database driver location.
     */
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    /**
     * The database connection object.
     */
    private static Connection conn = null;

    /**
     * Username for the database.
     */
    private static final String username = "U07NQ3"; // Username
    /**
     * Password for the database.
     */
    private static final String password = "53689076355"; // Password

    /**
     * Starts a connection to the database.
     *
     * @return Database connection.
     * @throws ClassNotFoundException From Class.forName().
     */
    public static Connection startConnection() throws ClassNotFoundException {

        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            //System.out.println("Connection Successful");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}
