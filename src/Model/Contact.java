package Model;

/**
 * Models a Contact object.
 *
 * @author Jonathan Waters
 */
public class Contact {

    /**
     * The contact database ID.
     */
    private int contactID;

    /**
     * The contact name.
     */
    private String contactName;

    /**
     * The contact email address.
     */
    private String email;

    /**
     * The no argument constructor.
     */
    public Contact() {
    }

    /**
     * The argument based constructor.
     *
     * @param contactName The contact name String.
     * @param email The contact email String.
     */
    public Contact(String contactName, String email) {
        this.contactName = contactName;
        this.email = email;
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
     * The getter for the contact email.
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * The setter for the contact email.
     *
     * @param email Contact email String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * toString() overridden for combo box display.
     *
     * @return Contact name String.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
