package Utils;

import DAO.AppointmentDAO;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Utility class that validates an Appointment.
 *
 * @author Jonathan Waters
 */
public class VerifyAppt {

    /**
     * The date time format.
     */
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    /**
     * Validates if an appointment has empty text fields.
     *
     * @param appointment The appointment to be validated
     * @return Boolean
     */
    public static boolean fieldsEmpty(Appointment appointment) {

        boolean fieldsEmpty = false;

        if (appointment.getTitle().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getDescription().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getLocation().equals("")) {
            fieldsEmpty = true;
        } else if (appointment.getType().equals("")) {
            fieldsEmpty = true;
        }

        if (fieldsEmpty) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("One or more text fields are empty.");
            alert.showAndWait();
        }

        return fieldsEmpty;
    }

    /**
     * Validates if an appointment's start is after it's end.
     *
     * @param appointment The appointment to be validated.
     * @return Boolean
     */
    public static boolean startAfterEnd(Appointment appointment) {

        boolean startAfterEnd = false;
        LocalDateTime startTime = appointment.getStartTime();
        LocalDateTime endTime = appointment.getEndTime();

        if (startTime.isAfter(endTime)) {
            startAfterEnd = true;
        }

        if (startAfterEnd) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The start time cannot occur after the end time.");
            alert.showAndWait();
        }

        return startAfterEnd;
    }

    /**
     * Validates if an appointment is outside business hours.
     *
     * @param appointment The appointment to be validated.
     * @return Boolean
     */
    public static boolean outsideBusinessHours(Appointment appointment) {

        //Build zoned appointment start and rezone as eastern time.
        LocalDateTime startDateTime = appointment.getStartTime();
        LocalDate startDate = startDateTime.toLocalDate();
        ZonedDateTime startTimeZoned = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime startTimeAsEST = startTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Build zoned business start time and zone as eastern time.
        LocalTime businessStartTime = LocalTime.parse("08:00:00");
        LocalDateTime businessStartDateTime = LocalDateTime.of(startDate, businessStartTime);
        ZonedDateTime businessStartTimeZoned = businessStartDateTime.atZone(ZoneId.of("America/New_York"));

        //Build zoned appointment end and rezone as eastern time.
        LocalDateTime endDateTime = appointment.getEndTime();
        ZonedDateTime endTimeZoned = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime endTimeAsEST = endTimeZoned.withZoneSameInstant(ZoneId.of("America/New_York"));

        //Build zoned business end time and zone as eastern time.
        //startDate is reused for business end because appointment cannot span multiple days.
        LocalTime businessEndTime = LocalTime.parse("22:00:00");
        LocalDateTime businessEndDateTime = LocalDateTime.of(startDate, businessEndTime);
        ZonedDateTime businessEndTimeZoned = businessEndDateTime.atZone(ZoneId.of("America/New_York"));

        boolean outsideBusinessHours = false;

        if (startTimeAsEST.isBefore(businessStartTimeZoned) ||
                endTimeAsEST.isAfter(businessEndTimeZoned)) {

            outsideBusinessHours = true;
        }

        if (outsideBusinessHours) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The appointment you trying to create occurs outside of business hours.\n" +
                    "Business hours are from 08:00 to 22:00 EST.");
            alert.showAndWait();
        }

        return outsideBusinessHours;
    }

    /**
     * Validates if an appointment overlaps with another appointment.
     *
     * @param newAppointment The appointment to be validated.
     * @return Boolean
     * @throws SQLException From AppointmentDAO.selectAll().
     */
    public static boolean overlappingAppointment(Appointment newAppointment) throws SQLException {

        ObservableList<Appointment> allAppointments = AppointmentDAO.selectAll();

        boolean overlapping = false;

        Appointment overlappingAppointment = new Appointment();

        for (Appointment appointment : allAppointments) {

            //Do not check newAppointment against itself.
            //Needed during check of modified appointment.
            if (appointment.getAppointmentID() == newAppointment.getAppointmentID()) {
                continue;
            }

            int newApptCustID = newAppointment.getCustomerID();
            LocalDateTime newApptStart = newAppointment.getStartTime();
            LocalDateTime newApptEnd = newAppointment.getEndTime();

            int apptCustID = appointment.getCustomerID();
            LocalDateTime apptStart = appointment.getStartTime();
            LocalDateTime apptEnd = appointment.getEndTime();

            overlappingAppointment = appointment;

            //If new appointment starts in the middle of other appointment
            if ((newApptCustID == apptCustID) && newApptStart.isAfter(apptStart) &&
                    newApptStart.isBefore(apptEnd)) {
                overlapping = true;
                break;
                //If new appointment ends in the middle of other appointment
            } else if ((newApptCustID == apptCustID) && newApptEnd.isAfter(apptStart) &&
                    newApptEnd.isBefore(apptEnd)) {
                overlapping = true;
                break;
                //If new appointment spans other appointment
            } else if ((newApptCustID == apptCustID) && newApptStart.isBefore(apptStart) &&
                    newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;
                //If new appointment starts at same time and ends after other appointment
            } else if ((newApptCustID == apptCustID) && newApptStart.isEqual(apptStart) &&
                    newApptEnd.isAfter(apptEnd)) {
                overlapping = true;
                break;
                //If new starts before and ends at same time as other appointment
            } else if ((newApptCustID == apptCustID) && newApptStart.isBefore(apptStart) &&
                    newApptEnd.isEqual(apptEnd)) {
                overlapping = true;
                break;
                //If new appointment starts and ends at same time as other appointment
            } else if ((newApptCustID == apptCustID) && newApptStart.isEqual(apptStart) &&
                    newApptEnd.isEqual(apptEnd)) {
                overlapping = true;
                break;
            }
        }
/*
        if (overlapping) {


            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("The appointment you are trying to create overlaps with " +
                        "another appointment starting at " + overlappingAppointment.getStartTime().format(dateTimeFormat) +
                        " and ending at " + overlappingAppointment.getEndTime().format(dateTimeFormat) + ".");
            alert.showAndWait();
        }

 */

        return overlapping;
    }
}
