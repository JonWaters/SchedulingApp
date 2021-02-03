package Model;

import DAO.ContactDAO;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * Wrapper class that extends Appointment for displaying appointments in the appointments
 * tableview.
 */
public class AppointmentDisplay extends Appointment {

    /**
     * The contact name associated with the appointment.
     */
    private String contactName;

    /**
     * The appointment start time as a String.
     */
    private String startTimeString;

    /**
     * The appointment end time as a String.
     */
    private String endTimeString;

    /**
     * Argument based constructor.
     *
     * @param appointment The appointment to be wrapped.
     * @throws SQLException From ContactDAO.selectByID().
     */
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

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-YYYY HH:mm");

        this.startTimeString = appointment.getStartTime().format(dateTimeFormat);
        this.endTimeString = appointment.getEndTime().format(dateTimeFormat);

    }

    /**
     * The getter for the contact name.
     *
     * @return String
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for the contact name.
     *
     * @param contactName Contact name String.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * The getter for the appointment start time String.
     *
     * @return String
     */
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * The setter for the appointment start time string.
     *
     * @param startTimeString Start time String.
     */
    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    /**
     * The getter for the appointment end time String.
     *
     * @return String
     */
    public String getEndTimeString() {
        return endTimeString;
    }

    /**
     * The setter for the appointment end time String.
     *
     * @param endTimeString End time String.
     */
    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }
}
