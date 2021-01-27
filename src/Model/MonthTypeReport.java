package Model;

import java.time.Month;

public class MonthTypeReport {

    private int year;

    private Month month;

    private String type;

    private int total;

    public MonthTypeReport() {
    }

    public MonthTypeReport(int year, Month month, String type, int total) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
