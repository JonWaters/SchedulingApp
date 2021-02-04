package Model;

import java.time.LocalDateTime;

/**
 * Models a Division object.
 *
 * @author Jonathan Waters
 */
public class Division {

    /**
     * The division database ID.
     */
    private int divisionID;

    /**
     * The division name.
     */
    private String divisionName;

    /**
     * The division create date.
     */
    private LocalDateTime createDate;

    /**
     * User who created the division.
     */
    private String createdBy;

    /**
     * The division last update time.
     */
    private LocalDateTime lastUpdateTime;

    /**
     * User who last updated the division.
     */
    private String lastUpdatedBy;

    /**
     * The country database ID.
     */
    private int countryID;

    /**
     * The no argument constructor.
     */
    public Division() {
    }

    /**
     * The argument based constructor.
     *
     * @param divisionName The division name.
     * @param createdBy User who created the division.
     * @param lastUpdatedBy User who last updated the division.
     * @param countryID The country database ID.
     */
    public Division(String divisionName, String createdBy, String lastUpdatedBy, int countryID) {
        this.divisionName = divisionName;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * The getter for the division database ID.
     *
     * @return Division database ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * The setter for the division database ID.
     *
     * @param divisionID Division database ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * The getter for the division name.
     *
     * @return Division name.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * The setter for the division name.
     *
     * @param divisionName Division name.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * The getter for the division create date.
     *
     * @return Division create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * The setter for the division create date.
     *
     * @param createDate Division create date.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * The getter for the division created by.
     *
     * @return User who created the division.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter for the division created by.
     *
     * @param createdBy User who created the division.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * The getter for the last update time.
     *
     * @return Last update time.
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * The setter for the last update time.
     *
     * @param lastUpdateTime last update time.
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * The getter for the last updated by.
     *
     * @return User who last updated the division.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * The setter for last updated by.
     *
     * @param lastUpdatedBy User who last updated the division.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The getter for the country database ID.
     *
     * @return Country database ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * The setter for the country database ID.
     *
     * @param countryID Country database ID.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * toString() overridden for combo box display.
     *
     * @return Division name.
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
