package Model;

public class MonthReportDisplay {

    private int year;

    private String month;

    private int total;

    public MonthReportDisplay() {
    }

    public MonthReportDisplay(int year, String month, int total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
