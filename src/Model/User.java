package Model;

import java.time.LocalDateTime;

/**
 * Models a User object.
 *
 * @author Jonathan Waters
 */
public class User {

    /**
     * The user database ID.
     */
    private int userID;

    /**
     * The user name.
     */
    private String userName;

    /**
     * The user password.
     */
    private String password;

    /**
     * Date the user was created.
     */
    private LocalDateTime createDate;

    /**
     * User who created the user.
     */
    private String createdBy;

    /**
     * Date the user was last updated.
     */
    private LocalDateTime lastUpdateTime;

    /**
     * User who last updated the user.
     */
    private String lastUpdatedBy;

    /**
     * The no argument constructor.
     */
    public User() {
    }

    /**
     * The argument based constructor.
     *
     * @param userName The user name.
     * @param password The user password.
     * @param createdBy User who created the user.
     * @param lastUpdatedBy User who last updated the user.
     */
    public User(String userName, String password, String createdBy, String lastUpdatedBy) {
        this.userName = userName;
        this.password = password;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The getter for the user database ID.
     *
     * @return User database ID.
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
     * The getter for the user name.
     *
     * @return User name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The setter for the user name.
     *
     * @param userName User name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The getter for the user password.
     *
     * @return User password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * The setter for the user password.
     *
     * @param password User password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The getter for the user create date.
     *
     * @return User create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * The setter for the user create date.
     *
     * @param createDate User create date.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * The getter for user created by.
     *
     * @return Created by user name.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter for user created by.
     *
     * @param createdBy Created by user name.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * The getter for the last update time.
     *
     * @return Date the user was last updated.
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * The setter for the last update time.
     *
     * @param lastUpdateTime Date the user was last updated.
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * The getter for the user last updated by.
     *
     * @return User name of user to perform last update.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * The setter for the user last updated by.
     *
     * @param lastUpdatedBy User name of user to perform last update.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * toString overridden for combo box display.
     *
     * @return User name.
     */
    @Override
    public String toString() {
        return userName;
    }
}
