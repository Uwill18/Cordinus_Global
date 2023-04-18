package cordinus.cordinus_global.utils;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.model.Appointment;

import java.time.*;

public class TimeUtil {
    private static final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));

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
//case 4 start w/i and is after, start after start andends after end
//how to differentiate instances of existing appointments from instances of appointments "to be", with new, we create
    //an instance, but how do we make sure it is the same instance? do we need references, and or pointers?
//https://java-programming.mooc.fi/part-5/4-objects-and-references

//            NAS = newAppointmentStart

//    EAS = existingAppointmentStart
//            EAE = existingAppointmentEnd
//if ((NAS > EAS) && (NAS < EAE))
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

    public static boolean appointmentOverlapCheck(){

    return true;
    }
}
