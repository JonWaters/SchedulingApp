package Test;

import DAO.AppointmentDAO;
import Main.Main;
import Model.Appointment;
import Utils.VerifyAppt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class OverlappingAppointmentTest {

    Appointment newAppointment = new Appointment("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2021, 4, 1,8, 0),
            LocalDateTime.of(2021, 4, 1, 9, 0), "Test User", "Test User",
            1, 1, 1);

    Appointment overlappingAppointment = new Appointment("Test Title", "Test Description",
            "Test Location", "Test Type", LocalDateTime.of(2021, 4, 1,8, 30),
            LocalDateTime.of(2021, 4, 1, 9, 0), "Test User", "Test User",
            1, 1, 1);

    //Create test appointment and insert in DB
    private void createTestRecord() throws SQLException {
        AppointmentDAO.create(newAppointment);
    }

    @Test
    void isOverlappingAppointment() throws SQLException {
        createTestRecord();
        Assertions.assertEquals(true, VerifyAppt.overlappingAppointment(overlappingAppointment));
    }
}
