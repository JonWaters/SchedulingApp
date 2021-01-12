package DAO;

import Model.Appointment;
import sample.Main;
import utils.DbQuery;

import java.sql.*;

public class AppointmentDAO {

    private final static Connection conn = Main.conn;

    public static void create(Appointment appointment) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO appointments(Title, Description, Location, +" +
                    "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                    "Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, appointment.getTitle());
            ps.setString(2,appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
            ps.setString(7, appointment.getCreatedBy());
            ps.setString(8, appointment.getLastUpdatedBy());
            ps.setInt(9, appointment.getCustomerID());
            ps.setInt(10, appointment.getUserID());
            ps.setInt(11, appointment.getContactID());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            appointment.setAppointmentID(rs.getInt(1));

            sqlStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";

            ps = DbQuery.getPreparedStatement();

            ps.setInt(1, appointment.getAppointmentID());

            ps.execute();

            rs = ps.getResultSet();
            rs.next();

            appointment.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            appointment.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
