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
    /** Learned from Mr. Ruiz that best practices when doing time comparisons are to do so
     * from the references ZonedateTime Object and User System Settings.*/
    public static boolean businessHoursCheck( LocalDateTime start, LocalDateTime end) {
        LocalDate selectedStart = start.toLocalDate();
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(selectedStart, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);

        /**selection can happen any time after the present date, but end date time values do not occur before  start date time values selected by the user*/

        if(start.isAfter(localEnd.toLocalDateTime()) || start.isEqual(localEnd.toLocalDateTime()) || start.isBefore(LocalDateTime.now())  || end.isBefore(localStart.toLocalDateTime()) || end.isAfter(localEnd.toLocalDateTime()) || end.isBefore(start) || end.equals(start)){
            return false;
        }else {
            return true;
        }
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


    /**https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=3eef99f0-356a-4422-b92b-adf900f99fec
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
     *
     * Second case of classes is:
     * s1 = s
     *
     *
     //                if(start.isEqual(appointment.getStart())){
     //                    return true;
     //                }
     *
     * Third case of classes is:
     * s1 > s && s1 < e
     *
     *
     //                if(start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())){
     //                    return true;
     //                }
     // I was also very grateful to get help from Mr. Wabara in analyzing additional cases for overlaps that occurred
     //post-testing. I have documented my understanding of the cases below, and their relationships to earlier code.
     *
     // * */

    public static boolean appointmentOverlapCheck( LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM APPOINTMENTS ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            //int appointmentID = rs.getInt("Appointment_ID");
            int customerID = rs.getInt("Customer_ID");

            ObservableList<Appointment> dayAppointments = AppointmentsQuery.getDayAppointments();

                for(Appointment appointment: dayAppointments){

                    if( (appointment.getCustomer_ID() != customerID)){
                        continue;
                    }


                    if(start.equals(appointment.getStart()) || end.equals(appointment.getEnd())){
                        //System.out.println("overlap");
                        /**exact overlap, s1 = s && e1 =e*/
                        break;
                    }
                    else if(start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())){
                        //System.out.println("overlap 2");
                        /** selected start within existing appoinmentment, s1 > s && s1 < e*/
                        return true;
                    }
                    else if(end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())){
                        //System.out.println("overlap 3");
                        /**new appointment ends during existing appointment*/
                        return true;
                    }
                    else if(appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end)){
                        //System.out.println("overlap 4");
                        /**existing appointment starts within time of new appointment, s1 < s && e1 > s*/
                        return true;
                    }
                    else if(appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)){
                        //System.out.println("overlap 5");
                        /**existing appointment ends within time of new appointment*/
                        return true;
                    }
            }
        }
        return false;
    }

}


