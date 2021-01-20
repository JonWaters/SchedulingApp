package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.Main;
import Utils.DbQuery;

import java.sql.*;

public class AppointmentDAO {

    private final static Connection conn = Main.conn;

    public static void create(Appointment appointment) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO appointments(Title, Description, Location, " +
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
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Appointment selectByID(int dbID) throws SQLException {

        Appointment appointment = new Appointment();

        try {
            String sqlStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setInt(1, dbID);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            appointment.setAppointmentID(rs.getInt("Appointment_ID"));
            appointment.setTitle(rs.getString("Title"));
            appointment.setDescription(rs.getString("Description"));
            appointment.setLocation(rs.getString("Location"));
            appointment.setType(rs.getString("Type"));
            appointment.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
            appointment.setEndTime(rs.getTimestamp("End").toLocalDateTime());
            appointment.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
            appointment.setCreatedBy(rs.getString("Created_By"));
            appointment.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
            appointment.setCustomerID(rs.getInt("Customer_ID"));
            appointment.setUserID(rs.getInt("User_ID"));
            appointment.setContactID(rs.getInt("Contact_ID"));
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return appointment;
    }

    public static ObservableList<Appointment> selectAll() throws SQLException {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT * FROM appointments";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStartTime(rs.getTimestamp("Start").toLocalDateTime());
                appointment.setEndTime(rs.getTimestamp("End").toLocalDateTime());
                appointment.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime());
                appointment.setCreatedBy(rs.getString("Created_By"));
                appointment.setLastUpdateTime(rs.getTimestamp("Last_Update").toLocalDateTime());
                appointment.setCustomerID(rs.getInt("Customer_ID"));
                appointment.setUserID(rs.getInt("User_ID"));
                appointment.setContactID(rs.getInt("Contact_ID"));

                appointments.add(appointment);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return appointments;
    }

    public static void update(Appointment appointment) throws SQLException {

        try {
            String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, " +
                    "Type = ?, Start = ?, End = ?, Last_update = NOW(), Last_Updated_By = ?, " +
                    "Customer_ID = ?, User_ID = ?, Contact_ID = ?, WHERE Appointment_ID = ?";

            DbQuery.setPreparedStatement(conn, sqlStatement);

            PreparedStatement ps = DbQuery.getPreparedStatement();

            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
            ps.setString(7, appointment.getLastUpdatedBy());
            ps.setInt(8, appointment.getCustomerID());
            ps.setInt(9, appointment.getUserID());
            ps.setInt(10, appointment.getContactID());
            ps.setInt(11, appointment.getAppointmentID());

            ps.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteByID(int dbID) throws SQLException {

        try{
            String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

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
