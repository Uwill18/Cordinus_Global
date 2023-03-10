package cordinus.cordinus_global.appointment;

public class AppointmentsList {

    private String Appointment_ID;



    private String Contact_ID;


    private String Description;


    private String End;


    private String Location;


    private String Start;

    private String Title;

    private String Type;

    private String Customer_ID;

    private String User_ID;



    public AppointmentsList(String appointment_ID, String title, String description, String location, String contact_ID, String type, String start, String end, String customerID, String userID  ) {
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


    public String getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(String appointment_ID) {
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

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
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

    public String getCustomer_ID() {
        return Customer_ID;
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




}
