package Model;

import DAO.ContactDAO;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AppointmentDisplay extends Appointment {

    private String contactName;

    private String startTimeString;

    private String endTimeString;

    public AppointmentDisplay(Appointment appointment) throws SQLException {

        this.appointmentID = appointment.appointmentID;
        this.title = appointment.title;
        this.description = appointment.description;
        this.location = appointment.location;
        this.type = appointment.type;
        this.startTime = appointment.startTime;
        this.endTime = appointment.endTime;
        this.createDate = appointment.createDate;
        this.createdBy = appointment.createdBy;
        this.lastUpdateTime = appointment.lastUpdateTime;
        this.lastUpdatedBy = appointment.lastUpdatedBy;
        this.customerID = appointment.customerID;
        this.userID = appointment.userID;
        this.contactID = appointment.contactID;

        try {
            this.contactName = ContactDAO.selectByID(appointment.getContactID()).getContactName();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        this.startTimeString = appointment.getStartTime().format(timeFormat);
        this.endTimeString = appointment.getEndTime().format(timeFormat);

    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }
}
