package Model;

import java.time.LocalDateTime;

/**
 * Models a Country object.
 *
 * @author Jonathan Waters
 */
public class Country {

    /**
     * The country database ID.
     */
    private int countryID;

    /**
     * The country name.
     */
    private String countryName;

    /**
     * The country create date.
     */
    private LocalDateTime createDate;

    /**
     * User who created the country.
     */
    private String createdBy;

    /**
     * The country update time.
     */
    private LocalDateTime lastUpdateTime;

    /**
     * User who updated the country.
     */
    private String lastUpdatedBy;

    /**
     * The no argument constructor.
     */
    public Country() {
    }

    /**
     * The argument based constructor.
     *
     * @param countryName The country name String.
     * @param createdBy User name who created the country.
     * @param lastUpdatedBy User name who updated the country.
     */
    public Country(String countryName, String createdBy, String lastUpdatedBy) {
        this.countryName = countryName;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The getter for the country database ID.
     *
     * @return int
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
     * The getter for the country name.
     *
     * @return String
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * The setter for the country name.
     *
     * @param countryName Country name.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * The getter for the country create date.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * The setter for the country create date.
     *
     * @param createDate Country create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * The getter for the country created by.
     *
     * @return String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter for the country created by.
     *
     * @param createdBy User who created the country.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * The getter for the last update time.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * The setter for the last update time.
     *
     * @param lastUpdateTime Last update time.
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * The getter for the last updated by.
     *
     * @return String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * The setter for the last updated by.
     *
     * @param lastUpdatedBy User who last updated the country.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * toString() overridden for combo box display.
     *
     * @return Country name
     */
    @Override
    public String toString() {
        return countryName;
    }
}
