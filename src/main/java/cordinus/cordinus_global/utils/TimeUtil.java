package cordinus.cordinus_global.utils;

import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.CustomerSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeUtil {
    private static final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
    private Appointment appointment;

    public static boolean businessHoursCheck( LocalDateTime start, LocalDateTime end){
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);

        if(end.isBefore(localStart.toLocalDateTime()) || start.isAfter(localEnd.toLocalDateTime())){
            return false;
        } else {
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
    ObservableList<Appointment> ScheduledAppts = FXCollections.observableArrayList();
    public static boolean appointmentOverlapCheck(Appointment appointment, CustomerSchedule customerSchedule){
        if(customerSchedule.getStart().isBefore(appointment.getStart()) && customerSchedule.getEnd().isAfter(appointment.getStart())){
            System.out.println("Overlap");
        }
        if(customerSchedule.getStart().isAfter(appointment.getStart()) && customerSchedule.getStart().isBefore(appointment.getEnd())){
            System.out.println("Overlap");
        }


        if(customerSchedule.getStart().equals(appointment.getStart())){
            System.out.println("Overlap");
        }



    return true;
    }

    //condensed forloop checking appointment id. if matches continue
}
