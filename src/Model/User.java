package Model;

import java.time.LocalDateTime;

public class User {

    private int userID;

    private String userName;

    private String password;

    private LocalDateTime createDate;

    private String createdBy;

    private LocalDateTime lastUpdateTime;

    private String lastUpdatedBy;

    public User() {
    }

    public User(String userName, String password, String createdBy, String lastUpdatedBy) {
        this.userName = userName;
        this.password = password;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return userName;
    }
}
