package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery {

    private static Statement statement;

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        statement = conn.prepareStatement(sqlStatement);
    }

    public static Statement getPreparedStatement() {

        return statement;
    }
}
