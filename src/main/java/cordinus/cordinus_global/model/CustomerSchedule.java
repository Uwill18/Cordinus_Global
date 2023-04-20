package cordinus.cordinus_global.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class CustomerSchedule {
    private static int customerID;
    private static int appoinmentID;
    private static LocalDateTime start;
    private static LocalDateTime end;
    private int Customer_ID;
    private int Appointment_ID;

    private LocalDateTime Start;

    private LocalDateTime End;

    public CustomerSchedule(int customer_ID, int appointment_ID, LocalDateTime start, LocalDateTime end) {
        Customer_ID = customer_ID;
        Appointment_ID = appointment_ID;
        Start = start;
        End = end;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public LocalDateTime getStart() {
        return Start;
    }

    public void setStart(LocalDateTime start) {
        Start = start;
    }

    public LocalDateTime getEnd() {
        return End;
    }

    public void setEnd(LocalDateTime end) {
        End = end;
    }

    public static CustomerSchedule validateAppt(int customerID, int appoinmentID, LocalDateTime Start,LocalDateTime End) {
        CustomerSchedule.customerID = customerID;
        CustomerSchedule.appoinmentID = appoinmentID;
        start = Start;
        end = End;
        // The needle to search for
        final CustomerSchedule schedule = new CustomerSchedule(customerID,appoinmentID, Start,End);
        ObservableList<CustomerSchedule> schedules = FXCollections.observableArrayList();

        // The needle will now be equals to list objects
        // regarding its equals method which only checks
        // for the name

        //final int index = schedules.indexOf(schedule);
        final int index = schedules.indexOf(appoinmentID);
        if (index != -1) {
            // Get the element at this position
           // return schedules.get(index);
            return schedule;
        } else {
            return null;
        }
    }
}
