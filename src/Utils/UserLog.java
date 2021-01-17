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

public class UserLog {

    static User currentUser = LoginController.getCurrentUser();

    static ZoneId utcZoneID = ZoneId.of("UTC");

    static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss a z");

    public static void writeLog(int entryType) throws IOException {

        LocalDateTime currentDateTimeUTC = LocalDateTime.now(utcZoneID);
        ZonedDateTime zonedDateTimeUTC = currentDateTimeUTC.atZone(utcZoneID);
        String dateTimeString = globalFormat.format(zonedDateTimeUTC);
        
        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
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
