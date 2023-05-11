package cordinus.cordinus_global.utils;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Appointment;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class TimeUtil {
    /**
     * Learned from Mr. Ruiz that best practices when doing time comparisons are to do so
     * from the references ZonedateTime Object and User System Settings.
     */
    public static boolean businessHoursCheck(LocalDateTime start, LocalDateTime end) {
        LocalDate selectedStart = start.toLocalDate();
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(selectedStart, LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);

        /**selection can happen any time after the present date, but end date time values do not occur before  start date time values selected by the user*/

        return !start.isAfter(localEnd.toLocalDateTime()) && !start.isEqual(localEnd.toLocalDateTime()) && !start.isBefore(LocalDateTime.now()) && !end.isBefore(localStart.toLocalDateTime()) && !end.isAfter(localEnd.toLocalDateTime()) && !end.isBefore(start) && !end.equals(start);
    }


//    case 1 identical or w/i (start after start and ends before end)
//case 2 start before and end after (start  before start and end after end)
//case 3 start before and is w/i, start before start, and end before end
//case 4 start w/i and is after, start after start and ends after end
//how to differentiate instances of existing appointments from instances of appointments "to be", with new, we create
    //an instance, but how do we make sure it is the same instance? do we need references, and or pointers?
//https://java-programming.mooc.fi/part-5/4-objects-and-references
//https://openclassrooms.com/en/courses/6031811-persist-your-java-applications-data-with-the-repository-pattern/6270111-bind-java-objects-to-a-database-schema

//            NAS = newAppointmentStart
//with newApptStart I need to compare an object with the appt to be inserted.
    //I thought I needed references in Java, but a new obj points to the same values in memory like a reference
    //to a newly inserted/instantiated object
    //the question stands of how to pull the values of the new Appt object that has been instantiated with
    //the values of the inserted object

    //apptid of -1
    //correct customer and with different appointmentID

    //Start, End, Customer_ID define distinct existing appts. new appts. are ! this
    //new ObservableList
    //LDT .isAfter() and .isBefore() already optimized
    //real-life let db do filtering for you, it will be able to process the results faster


    /**
     * Determines if an appointment defined by start and end overlap with another appointment
     * for the same customer and not colliding with itself
     *
     * @return true if no overlap, false otherwise
     * The Logic for this variable is reusable for Add Appointment Controller and the Modify Appointment Controller
     */


    /**
     * https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=3eef99f0-356a-4422-b92b-adf900f99fec
     * Reviewed the above video hosted by Carolyn Sher-DeCusatis after fine-tuning my time logic with her
     * appointment.getStart() = s
     * appointment.getEnd() = e
     * start = s1
     * end = e1
     * There are three cases to check for overlaps per the video.
     * First case is:
     * s1 < s && e1 > s
     * //                if(start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart())){
     * //                    return true;
     * //                }
     * <p>
     * Second case of classes is:
     * s1 = s
     * <p>
     * <p>
     * //                if(start.isEqual(appointment.getStart())){
     * //                    return true;
     * //                }
     * <p>
     * Third case of classes is:
     * s1 > s && s1 < e
     * <p>
     * <p>
     * //                if(start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())){
     * //                    return true;
     * //                }
     * // I was also very grateful to get help from Mr. Wabara in analyzing additional cases for overlaps that occurred
     * //post-testing. I have documented my understanding of the cases below, and their relationships to earlier code.
     * <p>
     * // *
     */

    public static boolean appointmentOverlapCheck(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM APPOINTMENTS ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //boolean isOverlapping = false;
        ObservableList<Appointment> dayAppointments = AppointmentsQuery.getDayAppointments();
        for (Appointment appointment : dayAppointments) {

            if (rs.next()) {
                //int appointmentID = rs.getInt("Appointment_ID");
                int customerID = rs.getInt("Customer_ID");
                if ((appointment.getCustomer_ID() != customerID)) {
                    continue;
                }
            }


            if (start.equals(appointment.getStart()) || end.equals(appointment.getEnd())) {
                System.out.println("overlap");
                /**exact overlap, s1 = s && e1 =e*/
                //isOverlapping = true;
                //return isOverlapping;
                //break;
                return true;
            }

            if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                System.out.println("overlap 2");
                /** selected start within existing appointment, s1 > s && s1 < e*/
                // isOverlapping = true;
                //return isOverlapping;
                return true;
                //break;
            }

            if (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) {
                System.out.println("overlap 3");
                /**new appointment ends during existing appointment*/
                //isOverlapping = true;
                return true;
                //break;
            }

            if (appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end)) {
                System.out.println("overlap 4");
                /**existing appointment starts within time of new appointment, s1 < s && e1 > s*/
                //isOverlapping = true;
                // return isOverlapping;
                return true;
                //break;
            }

            if (appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)) {
                System.out.println("overlap 5");
                /**existing appointment ends within time of new appointment*/
                //isOverlapping = true;
                //return isOverlapping;
                return true;
                //break;
            }
        }
        return false;
    }

    /**This method gets the selected Appointment object, and compares the properties of start and end gathered
     * from the below sql query for appointment overlaps. This approach is effective for this use case because
     * the modify appointments requires a selection to manipulate the object whereas add appointments
     * deals in creating new appointment objects.*/


    public static boolean appointmentModOverlapCheck(Appointment appointment) throws SQLException {
        String sql = "SELECT  Start, End  FROM APPOINTMENTS WHERE Customer_ID =?";
        //String sql = "SELECT  Start, End  FROM APPOINTMENTS WHERE Customer_ID =? AND Appointment_ID=?";
        //String sql = "SELECT  Start, End  FROM APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,appointment.getCustomer_ID());
        //ps.setInt(2,appointment.getAppointment_ID());
        ResultSet rs = ps.executeQuery();


            while (rs.next()) {

            LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();



                if ((appointment.getStart().equals(start) || appointment.getEnd().equals(end))) {
                System.out.println("overlap");
                /**exact overlap, s1 = s && e1 = e*/
                //isOverlapping = true;
                //return isOverlapping;
                //break;
                return true;
            }

            if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())  ) {
                System.out.println("overlap 2");
                /** selected start within existing appointment, s1 > s && s1 < e*/
                // isOverlapping = true;
                //return isOverlapping;
                return false;
                //break;
            }

            if (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) {
                System.out.println("overlap 3");
                /**new appointment ends during existing appointment*/
                //isOverlapping = true;
                return false;
                //break;
            }

            if (appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end)) {
                System.out.println("overlap 4");
                /**existing appointment starts within time of new appointment, s1 < s && e1 > s*/
                //isOverlapping = true;
                // return isOverlapping;
                return false;
                //break;
            }

            if (appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)) {
                System.out.println("overlap 5");
                /**existing appointment ends within time of new appointment*/
                //isOverlapping = true;
                //return isOverlapping;
                return false;
                //break;
            }
        }
        return true;
    }

    public static boolean appointmentOverlapModCheck(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //boolean isOverlapping = false;


        if (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            int customerID = rs.getInt("Customer_ID");
            ObservableList<Appointment> dayAppointments = AppointmentsQuery.getDayAppointments();
            for (Appointment appointment : dayAppointments) {

                //((appointmentID != appointment.getAppointment_ID()) || (customerID != appointment.getCustomer_ID()))
                if ((appointmentID == appointment.getAppointment_ID())) {
                    return true;
                } else {

                    if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                        System.out.println("overlap 2");
                        /** selected start within existing appointment, s1 > s && s1 < e*/
                        // isOverlapping = true;
                        //return isOverlapping;
                        return true;
                        //break;
                    }

                    if (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) {
                        System.out.println("overlap 3");
                        /**new appointment ends during existing appointment*/
                        //isOverlapping = true;
                        return true;
                        //break;
                    }

                    if (appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end)) {
                        System.out.println("overlap 4");
                        /**existing appointment starts within time of new appointment, s1 < s && e1 > s*/
                        //isOverlapping = true;
                        // return isOverlapping;
                        return true;
                        //break;
                    }

                    if (appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)) {
                        System.out.println("overlap 5");
                        /**existing appointment ends within time of new appointment*/
                        //isOverlapping = true;
                        //return isOverlapping;
                        return true;
                        //break;
                    }

//            if(start.equals(appointment.getStart()) || end.equals(appointment.getEnd())){
//                System.out.println("overlap");
//                /**exact overlap, s1 = s && e1 =e*/
//                //isOverlapping = true;
//                //return isOverlapping;
//                //break;
//                return true;
//            }

                    return false;
                }


            }

        }

        return false;

    }
}



