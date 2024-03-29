package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.ContactsQuery;

import java.time.LocalDateTime;
import java.util.List;


/**Used standard variables according to the database and functional requisites Initialed constructors, getters, and setters according to my initial understanding of the data types.
 * In the future I would do more UML diagramming to align myself better with best practices */

public class Appointment {
    private int Appointment_ID;
    private int Contact_ID;
    private String Description;
    private LocalDateTime End;
    private String Location;
    private LocalDateTime Start;
    private String Title;
    private String Type;
    private int Customer_ID;
    private String User_ID;


    public Appointment(int appointment_ID, String title, String description, String location, String contact_ID, String type, LocalDateTime start, LocalDateTime end, String customerID, String userID  ) {
        Appointment_ID = appointment_ID;//autogen
        Title = title;
        Description = description;
        Location = location;
        Contact_ID = Integer.parseInt(contact_ID);
        Type = type;
        Start = start;
        End = end;
        Customer_ID = Integer.parseInt(customerID);
        User_ID = userID;
    }


    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String appointmentMonth;
    public int appointmentTotal;
    public String appointmentType;

    /**used getters and setters for localdatetime objects under type of LocalDatetime,
     * that way it is easier to send back as LocalDate or Localtime, from the timestamps*/
    public LocalDateTime getEnd() {
        return End;
    }
    public void setEnd(LocalDateTime end) {
        End = end;
    }
    public LocalDateTime getStart() {
        return Start;
    }
    public void setStart(LocalDateTime start) {
        Start = start;
    }
    public String getLocation() {
        return Location;
    }
    public void setLocation(String location) {
        Location = location;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public int getCustomer_ID() {
        return Integer.parseInt(String.valueOf(Customer_ID));
    }
    public void setCustomer_ID(String customer_ID) {
        Customer_ID = Integer.parseInt(customer_ID);
    }
    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    //public Contact getContact(){
        //return ;
    //}

/**applied same logic as getCountryByDivision*/
    public Customer getCustomer_Name(){
        return AppointmentsQuery.getCustomerByID(Customer_ID);
    }

    public String toString(){
        return String.valueOf(Customer_ID);
    }

}
