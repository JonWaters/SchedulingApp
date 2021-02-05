package Utils;

import Controller.LoginController;
import Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class that creates a a user log.
 *
 * @author Jonathan Waters
 */
public class UserLog {

    /**
     * The user currently logged in.
     */
    static User currentUser;

    /**
     * The UTC timezone ID.
     */
    static ZoneId utcZoneID = ZoneId.of("UTC");

    /**
     * The date time format.
     */
    static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss z");

    /**
     * Records user login attempts to a log file with timestamp.
     *
     * @param entryType int for successful or unsuccessful log entry type.
     * @throws IOException From FileWriter().
     */
    public static void writeLog(int entryType) throws IOException {

        LocalDateTime currentDateTimeUTC = LocalDateTime.now(utcZoneID);
        ZonedDateTime zonedDateTimeUTC = currentDateTimeUTC.atZone(utcZoneID);
        String dateTimeString = globalFormat.format(zonedDateTimeUTC);

        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            currentUser = LoginController.getCurrentUser();
            String userName = currentUser.getUserName();

            if (userName == null) {
                userName = "Unknown user";
            }

            if (entryType == 0) {
                printWriter.println(userName + " was denied access on " + dateTimeString);
                printWriter.close();
            }

            if (entryType == 1) {
                printWriter.println(userName + " was granted access on " + dateTimeString);
                printWriter.close();
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
