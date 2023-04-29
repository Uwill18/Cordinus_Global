package cordinus.cordinus_global.model;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.ContactsQuery;

import java.time.LocalDateTime;
import java.util.List;

public class Appointment {

    private int Appointment_ID;
    private String Contact_ID;
    private String Description;
    private LocalDateTime End;
    private String Location;
    private LocalDateTime Start;
    private String Title;
    private String Type;
    private String Customer_ID;
    private String User_ID;
    public static Appointment appointment;


    public Appointment(int appointment_ID, String title, String description, String location, String contact_ID, String type, LocalDateTime start, LocalDateTime end, String customerID, String userID  ) {
        Appointment_ID = appointment_ID;//autogen
        Title = title;
        Description = description;
        Location = location;
        Contact_ID = contact_ID;
        Type = type;
        Start = start;
        End = end;
        Customer_ID = customerID;
        User_ID = userID;
        //add customer_id
        //user_id


    }


    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public String getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(String contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

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
        return Integer.parseInt(Customer_ID);
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public List<Contact> getContact(){
        return ContactsQuery.getAllContacts();
    }

    public String toString(){
        return Start.toString() +" "+End.toString()+"\n";
    }

    public String getCustomerByID(){
        return AppointmentsQuery.getCustomerByID(Integer.parseInt(Customer_ID));
    }

}
