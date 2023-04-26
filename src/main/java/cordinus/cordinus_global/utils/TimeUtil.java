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
//    private static final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
//    // private static final ZonedDateTime EST_BH_START_RANGE = ZonedDateTime.of(LocalDate.now().plusMonths(1), LocalTime.of(8,0), ZoneId.of("America/New_York"));
//


    public static boolean businessHoursCheck( LocalDateTime start, LocalDateTime end) {
        LocalDate selectedStart = start.toLocalDate();
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(selectedStart, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        // private static final ZonedDateTime EST_BH_START_RANGE = ZonedDateTime.of(LocalDate.now().plusMonths(1), LocalTime.of(8,0), ZoneId.of("America/New_York"));


        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);
        ZonedDateTime selectionRange = localEnd.plusMonths(1);

        if(end.isAfter(selectionRange.toLocalDateTime()) || localStart.isAfter(localEnd) || localStart.isEqual(localEnd)){
            return false;
        }else {
            //start.isBefore(ChronoLocalDateTime.from(localEnd)) && end.isBefore(ChronoLocalDateTime.from(localEnd)) || end.equals(localEnd)
            return true;
        }

       // return true;


        // ZonedDateTime selectionRange = EST_BH_START_RANGE.withZoneSameInstant(ZoneId.systemDefault());
        //ValueRange selectionRange = localStart.range(ChronoField.MONTH_OF_YEAR);

        //https://www.google.com/search?q=how+to+use+Zondedatetime+to+filter+day+selection+within+a+month&rlz=1C1VDKB_enUS1022US1022&oq=how+to+use+Zondedatetime+to+filter+day+selection+within+a+month&aqs=chrome..69i57.14196j0j4&sourceid=chrome&ie=UTF-8
//https://stackoverflow.com/questions/26824020/java-check-if-a-given-date-is-within-current-month
//        if(localStart.getMonth() == LocalDate.now().getMonth()){
//            if(end.isBefore(localStart.toLocalDateTime()) || start.isAfter(localEnd.toLocalDateTime())){
//                return false;
//            } else {
//                return true;
//            }
//
//        }
//        return true;
//    }
//
//        if(end.isBefore(localStart.toLocalDateTime()) || start.isAfter(localEnd.toLocalDateTime()) || start.isAfter(selectionRange.toLocalDateTime())){
//            return false;
//        } else {
//            return true;
//        }

//        if(end.isAfter(selectionRange.toLocalDateTime()) || start.isAfter(selectionRange.toLocalDateTime())){
//            return false;
//        }else if(end.isBefore(localStart.toLocalDateTime())){
//            return true;
//        }
//
//        return true;

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
//with newApptStart I need to compare an object with the appt to be inserted..
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

//CustomerSchedule
    //Start LDT
    //End LDT
    //Appt_ID
    //Cust_ID

    //CSQ
    //parameter is queried for

    //query parameters are stored inside object for comparison




    //    EAS = existingAppointmentStart     , CustomerSchedule.Start()
    //Thinking of grabbing the  start from FXCollections
//            EAE = existingAppointmentEnd   , CustomerSchedule.End()
//if ((NAS > EAS) && (NAS < EAE)) ,,  x
//    error
//else if ((NAS < EAS)&&(NAE > EAE))
//    error
//else if((NAE>NAS)&&(NAE < EAE))
//    error
//else if((NAS==EAS)||(NAE == EAE))
//
//    in AddAppt compare CustID, in ModAppt compare for not existing ApptID
//
//if no appt -1

    //private static final Appointment appointment = null;


    /**
     * Determines if an appointment defined by start and end overlap with another appointment
     * for the same customer and not colliding with itself
     *
     * @return true if no overlap, false otherwise
     * The Logic for this variable is reusable for Add Appointment Controller and the Modify Appointment Controller
     */
    public static boolean appointmentOverlapCheck() throws SQLException {

        String sql = "SELECT Appointment_ID, Customer_ID, Start, End  FROM APPOINTMENTS ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            int customerID = rs.getInt("Customer_ID");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

            ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
            for (Appointment appointment: allAppointments ) {
                if((appointment.getCustomer_ID() != customerID)){
                    continue;
                }
                if(start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart())){
                    System.out.println("Overlap 1");
                    Alerts.overlapWarning();
                }
                if(start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())){
                    System.out.println("Overlap 2");
                    Alerts.overlapWarning();
                }
                if(start.equals(appointment.getStart())){
                    System.out.println("Overlap 3");
                    Alerts.overlapWarning();
                }
            }

        }

        return true; //after the foreach loop return true
    }

    //condensed forloop checking appointment id. if matches continue
}
