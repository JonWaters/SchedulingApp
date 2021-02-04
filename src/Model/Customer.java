package Model;

import java.time.LocalDateTime;

/**
 * Models a Customer object.
 *
 * @author Jonathan Waters
 */
public class Customer {

    /**
     * The customer database ID.
     */
    protected int customerID;

    /**
     * The customer name.
     */
    protected String customerName;

    /**
     * The customer address.
     */
    protected String address;

    /**
     * The customer postal code.
     */
    protected String postalCode;

    /**
     * The customer phone number.
     */
    protected String phone;

    /**
     * The customer create date.
     */
    protected LocalDateTime createDate;

    /**
     * User who created the customer.
     */
    protected String createdBy;

    /**
     * When the customer was last updated.
     */
    protected LocalDateTime lastUpdateTime;

    /**
     * User who last updated the customer.
     */
    protected String lastUpdatedBy;

    /**
     * The division database ID
     */
    protected int divisionID;

    /**
     * The no argument constructor.
     */
    public Customer() {
    }

    /**
     * The argument based constructor.
     *
     * @param customerName The customer name.
     * @param address The customer address.
     * @param postalCode The customer postal code.
     * @param phone The customer phone number.
     * @param createdBy User who created the customer.
     * @param lastUpdatedBy User who last updated the customer.
     * @param divisionID The division database ID.
     */
    public Customer(String customerName, String address, String postalCode,
                    String phone, String createdBy, String lastUpdatedBy, int divisionID) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
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
     * The setter for customer database ID.
     *
     * @param customerID Customer database ID.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * The getter for the customer name.
     *
     * @return String
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The setter for the customer name.
     *
     * @param customerName Customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * The getter for customer address.
     *
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * The setter for the customer address.
     *
     * @param address Customer address String.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * The getter for customer postal code.
     *
     * @return Postal code String.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The setter for customer postal code.
     *
     * @param postalCode Postal code String.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * The getter for customer phone number.
     *
     * @return Phone number String.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * The setter for customer phone number.
     *
     * @param phone Phone number String.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * The getter for the customer create date.
     *
     * @return Create date LocalDateTime.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * The setter for the customer create date.
     *
     * @param createDate Create date LocalDateTime.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * The getter for the customer created by.
     *
     * @return User who created the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter for the customer created by.
     *
     * @param createdBy User who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * The getter for the customer last update time.
     *
     * @return Last update time LocalDateTime.
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * The setter for the customer last update time.
     *
     * @param lastUpdateTime Last update LocalDateTime.
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * The getter for the customer last updated by.
     *
     * @return User who last updated the customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * The setter for the customer last updated by.
     *
     * @param lastUpdatedBy User who last updated the customer.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The getter for the division database ID.
     *
     * @return Division database ID int.
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
     * toSting() overridden for combo box display.
     *
     * @return Customer name.
     */
    @Override
    public String toString() {
        return customerName;
    }
}
