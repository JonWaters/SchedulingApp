package Model;

import java.time.Month;

/**
 * Models the structure needed for display of the month/type report tableview.
 */
public class MonthTypeReport {

    /**
     * The year of the appointment.
     */
    private int year;

    /**
     * The month of the appointment.
     */
    private Month month;

    /**
     * The appointment type.
     */
    private String type;

    /**
     * The total number of appointments with same year, month, and type.
     */
    private int total;

    /**
     * The no argument constructor.
     */
    public MonthTypeReport() {
    }

    /**
     * The argument based constructor.
     *
     * @param year Appointment year.
     * @param month Appointment month.
     * @param type Appointment type.
     * @param total Appointment total based on year, month, and type
     */
    public MonthTypeReport(int year, Month month, String type, int total) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * The getter for the appointment year.
     *
     * @return Appointment year.
     */
    public int getYear() {
        return year;
    }

    /**
     * The setter for the appointment year.
     *
     * @param year Appointment year.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * The getter for the appointment month.
     *
     * @return Appointment month.
     */
    public Month getMonth() {
        return month;
    }

    /**
     * The setter for the appointment month.
     *
     * @param month Appointment month.
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * The getter for the appointment type.
     *
     * @return Appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * The setter for the appointment type.
     *
     * @param type Appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getter for the appointment total.
     *
     * @return Appointment total.
     */
    public int getTotal() {
        return total;
    }

    /**
     * The setter for the appointment total.
     *
     * @param total Appointment total.
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
