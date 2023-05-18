package cordinus.cordinus_global.utils;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.Appointment;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

public class TimeUtil {
    /**
     * Learned from Mr. Ruiz that best practices when doing time comparisons are to do so
     * from the references ZonedateTime Object and User System Settings.
     * selection can happen any time after the present date, but end date time values do not occur before  start date time values selected by the user*/

    public static boolean businessHoursCheck(LocalDateTime start, LocalDateTime end) {
        LocalDate selectedStart = start.toLocalDate();
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(selectedStart, LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = localStart.plusHours(14);

        return !start.isAfter(localEnd.toLocalDateTime()) && !start.isEqual(localEnd.toLocalDateTime()) && !start.isBefore(LocalDateTime.now()) && !end.isBefore(localStart.toLocalDateTime()) && !end.isAfter(localEnd.toLocalDateTime()) && !end.isBefore(start) && !end.equals(start);
    }




    /**This method gets the selected Appointment object, and compares the properties of start and end gathered
     * from the below sql query for appointment overlaps. This approach using polymorphism is effective for this use case because
     * the Modify Appointments Class/Object(s) require(s) a selection to manipulate the object whereas add appointments
     * deals in creating new appointment objects. Big Shout-Out to Mr. Wabara and Mr. Ruiz for guiding me through the logic
     * that helped me choose how to implement this function. Mr.Wabara guided me to sorting the correct query to use, and
     * Mr. Ruiz opened my eyes to best practices as well as implementing proof of contradiction for the appointment logic.
     * I'd also like to thank to Sunitha Kandalam and Carolyn Sher DeCusatis
     * for helping me further understand time logic.<br/>
     * <br/>
     * The logical equivalencies below express correlation to prior time logic implementation by proof of contradiction:<br/>
     * <br/>
     * s1 < e, selected start within existing appointment, s1 > s && s1 < e*<br/>
     * s1 != e, prevent exact overlap,exact overlap, s1 = s && e1 =e<br/>
     *  e1 > s ,existing appointment starts within time of new appointment, s1 < s && e1 > s<br/>
     *  e != s, prevent exact overlap, s1 = s && e1 =e<br/>
     * if any of the cases are true, then there is no overlap and the code block does not execute.<br/>
     * else, it will.<br/>
     * <br/>
     * for a larger scale company, this line of code would be preferred:
     * for(Appointment appointment: AppointmentsQuery.getCustomerAppointments(customerID))
     * this would allow different customers to book the same time if there were multiple professionals to be seen at the
     * same time while excluding the same customer from overlapping themselves.
     * In treated this case as if this were a small business who can only book with one professional, ergo
     * why I am choosing to execute the code : for(Appointment appointment: AppointmentsQuery.getYearAppointments(), instead.
     * In this case the user can book appointments for up to a year with no overlaps.
     * I did not design the code to book farther than a year since it may not be practical for the business, or the customer.
     * */



    public static boolean appointmentOverlapCheck(LocalDateTime start, LocalDateTime end, int appointmentID) throws SQLException {

        for(Appointment appointment: AppointmentsQuery.getYearAppointments()){
      //  for(Appointment appointment: AppointmentsQuery.getCustomerAppointments(customerID)){ // if it doesn't matter if different customers book the same time
                if (appointment.getAppointment_ID() == appointmentID){ //
                    continue;
                }
                if(!(start.isAfter(appointment.getEnd()) || start.equals(appointment.getEnd()) || end.isBefore(appointment.getStart()) || end.equals(appointment.getStart())) || appointment.getStart().equals(start)  || appointment.getEnd().equals(end) ){

                        return true;
                }
                //return false;
        }
        return false;
    }

}



