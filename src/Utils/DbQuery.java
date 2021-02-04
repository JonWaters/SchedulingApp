package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class that creates a database query statement.
 *
 * @author Jonathan Waters
 */
public class DbQuery {

    /**
     * The prepared SQL statement.
     */
    private static PreparedStatement statement;

    /**
     * Creates a prepared SQL statement.
     *
     * @param conn The database connection.
     * @param sqlStatement The SQL statement.
     * @throws SQLException From conn.prepareStatement().
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        statement = conn.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    /**
     * The getter for the prepared statement.
     *
     * @return SQL prepared statement.
     */
    public static PreparedStatement getPreparedStatement() {

        return statement;
    }
}
