package Model;

import java.time.LocalDateTime;

/**
 * Models an Appointment object.
 */
public class Appointment {

    /**
     * The appointment database ID.
     */
    protected int appointmentID;

    /**
     * The appoint title.
     */
    protected String title;

    /**
     * The appointment description.
     */
    protected String description;

    /**
     * The appointment location.
     */
    protected String location;

    /**
     * The appointment type.
     */
    protected String type;

    /**
     * The appointment start time.
     */
    protected LocalDateTime startTime;

    /**
     * The appointment end time.
     */
    protected LocalDateTime endTime;

    /**
     * The appointment create date.
     */
    protected LocalDateTime createDate;

    /**
     * The user who created the appointment.
     */
    protected String createdBy;

    /**
     * The appointment update time.
     */
    protected LocalDateTime lastUpdateTime;

    /**
     * The user who updated the appointment.
     */
    protected String lastUpdatedBy;

    /**
     * The customer database ID.
     */
    protected int customerID;

    /**
     * The user database ID.
     */
    protected int userID;

    /**
     * The contact database ID.
     */
    protected int contactID;

    /**
     * The no argument constructor.
     */
    public Appointment() {
    }

    /**
     * The argument based constructor.
     *
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param startTime The appointment start time.
     * @param endTime The appointment end time.
     * @param createdBy User who created the appointment.
     * @param lastUpdatedBy User who last updated the appointment.
     * @param customerID The customer database ID.
     * @param userID The user database ID.
     * @param contactID The contact database ID.
     */
    public Appointment(String title, String description, String location,
                       String type, LocalDateTime startTime, LocalDateTime endTime,
                       String createdBy, String lastUpdatedBy, int customerID, int userID,
                       int contactID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * The getter for the appointment database ID.
     *
     * @return int
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * The setter for the appointment database ID.
     *
     * @param appointmentID Appointment database ID.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * The getter for the appointment title.
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setter for the appointment title.
     *
     * @param title Appointment title String.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter for the appointment description.
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter for the appointment description.
     *
     * @param description Appointment description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getter for the appointment location.
     *
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setter for the appointment location.
     *
     * @param location Appointment location String.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getter for the appointment type.
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * The setter for the appointment type.
     *
     * @param type Appointment type String.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getter for the appointment start time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * The setter for the appointment start time.
     *
     * @param startTime Appointment start LocalDateTime.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * The getter for the appointment end time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * The setter for the appointment end time.
     *
     * @param endTime Appointment end LocalDateTime.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * The getter for appointment create date.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * The setter for the appointment create date.
     *
     * @param createDate Appointment creation LocalDateTime.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * The getter for the appointment created by.
     *
     * @return String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter for the appointment created by.
     *
     * @param createdBy Appointment created by String.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * The getter for the appointment last update time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * The setter for the appointment last update time.
     *
     * @param lastUpdateTime Appointment last update LocalDateTime.
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * The getter for the appointment last updated by
     *
     * @return String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * The setter for the appointment last updated by.
     *
     * @param lastUpdatedBy Appointment updated by String.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The getter for the customer database ID.
     *
     * @return int
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * The setter for the customer database ID.
     *
     * @param customerID Customer database ID.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * The getter for the user database ID.
     *
     * @return int
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The setter for the user database ID.
     *
     * @param userID User database ID.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * The getter for the contact database ID.
     *
     * @return int
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * The setter for the contact database ID.
     *
     * @param contactID Contact database ID.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
