package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.DAO.CustomersQuery;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.*;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.utils.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.parse;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
//https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class AddApptController implements Initializable {
    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private DatePicker StartDatePicker;
    public ComboBox<LocalTime> StartTimeCombo;
    public ComboBox<LocalTime> EndTimeCombo;
    @FXML
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private ComboBox<User> UserComboBox;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ComboBox<String> TypeComboBox;
    @FXML
    private Label typeLbl;
    @FXML
    private Label startTimeLbl;
    @FXML
    private Label endTimeLbl;
    @FXML
    private TextField ApptIDTxt;
    @FXML
    private Label appointmentIDLbl;
    @FXML
    private TextField ContactTxt;
    @FXML
    private Label contactLbl;
    @FXML
    private TextField DescriptionTxt;
    @FXML
    private Label descriptionLbl;
    @FXML
    private TextField LocationTxt;
    @FXML
    private Label locationLbl;
    @FXML
    private TextField TitleTxt;
    @FXML
    private Label titleLbl;
    @FXML
    private TextField CustomerIDTxt;
    @FXML
    private Label customerIDLbl;
    @FXML
    private TextField UserIDTxt;
    @FXML
    private Label userIDLbl;
    @FXML
    private Button addBtn;
    @FXML
    private Button backBtn;
    private Customer customer;
    private int index;
    private User user;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int h = 0; h < 23; h++) {
            getStartTimeCombo(h, StartTimeCombo, EndTimeCombo);
        }
        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for (int i = 0; i < 3; i++) {
            TypeComboBox.getItems().add((i + 1) + " | " + typeAppt[i]);
        }
        //Set the values to current date and time to make selection easier for the user
        StartDatePicker.setValue(LocalDate.now());
        EndDatePicker.setValue(LocalDate.now());
        try {
            nextApptCheck();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ContactComboBox.setItems(ContactsQuery.getAllContacts());
        CustomerComboBox.setItems(CustomersQuery.getAllCustomers());
        //CustomerComboBox.setValue(AppointmentsQuery.getCustomerByID(customer.getCustomer_ID()));
        UserComboBox.setItems(UsersQuery.getAllUsers());
        UserComboBox.setValue(UsersQuery.user);
        userUpdate();

        /////////////////////////
        appointmentIDLbl.setText(rb.getString("AppointmentID"));
        userIDLbl.setText(rb.getString("UserID"));
        customerIDLbl.setText(rb.getString("CustomerID"));
        titleLbl.setText(rb.getString("Title"));
        descriptionLbl.setText(rb.getString("Description"));
        locationLbl.setText(rb.getString("Location"));
        startTimeLbl.setText(rb.getString("Start"));
        endTimeLbl.setText(rb.getString("End"));
        contactLbl.setText(rb.getString("Contact")+" "+rb.getString("ID") +" & "+rb.getString("Name"));
        typeLbl.setText(rb.getString("Type"));

        addBtn.setText(rb.getString("Add"));
        backBtn.setText(rb.getString("Back"));
        ///////////////////////////

    }



    /**Timing nested loop
     //Mr. Wabara reviewed time loops with me to output to the combobox.
     //I learned that combining the times with the date picker to a timestamp
     //would help format the time, and this skill was transferable as well to implementing
     the TypeComboBox as shown below, and the ContactComboBox.*/
    /**The Intervals are hard coded for appt start-times and appt end-times.
     * This function was generated by the ide based off of the prior implementation.*/

    static void getStartTimeCombo(int h, ComboBox<LocalTime> startTimeCombo, ComboBox<LocalTime> endTimeCombo) {
        startTimeCombo.getItems().add(LocalTime.of(h, 0));
        startTimeCombo.getItems().add(LocalTime.of(h, 15));
        startTimeCombo.getItems().add(LocalTime.of(h, 30));
        startTimeCombo.getItems().add(LocalTime.of(h, 45));

        endTimeCombo.getItems().add(LocalTime.of(h, 0));
        endTimeCombo.getItems().add(LocalTime.of(h, 15));
        endTimeCombo.getItems().add(LocalTime.of(h, 30));
        endTimeCombo.getItems().add(LocalTime.of(h, 45));
    }

    /**
     * <p>Here my ComboBoxes <b>StartDatePicker</b> & <b>EndDatePicker</b>list local time objects
     * //then upon user selection, gets the value to be inserted for the times as a timestamp,
     * //making timeformatting as a sting not necessary in this case.
     * // The db takes said timestamp and converts it to sql server timezone
     * //easier than it would a string, especially if it is set to UTC</p>
     */

    /**Reviewed with Sunitha Kandalam,Juan Ruiz, Malcolm Wabara, and Carolyn Sher Decusatis the logic for verifying which appointments to insert.
     * Since this check is to verify IF something should be inserted, I thought a boolean
     * implementation similar to UsersQuery.userConfirmation(username,password) from my LoginController and the
     * CustomersQuery.deleteConfirmation(custID) in my  OnDeleteCustomer from the CustomerController would apply.
     * I learned to simplify, and to check when the appointmentOverlapCheck is not false to insert,
     * and otherwise alert if either of the boolean are false as below, as opposed to each time the functions
     * showed false. I also learned how to implement this in most reduced form so it could be reusable for the modification overlap,
     * and how to prevent overlaps for up to a year in booking.*/

    @FXML
    public void insertAppt(ActionEvent event) throws SQLException, IOException {

        int userid = Integer.parseInt(UserIDTxt.getText()); //get from combobox
        int custid = Integer.parseInt(CustomerIDTxt.getText()); // get from combobox
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contact = ContactComboBox.getValue().getContact_ID();
        String type = TypeComboBox.getValue();

        LocalDateTime date = LocalDateTime.now();//use LocalDateTime
        Timestamp timestamp = Timestamp.valueOf(date);

        Timestamp createDate = timestamp;
        Timestamp lastUpdate = timestamp;


        String createdBy = UsersQuery.getCurrentUserData().getUser_Name();
        String lastUpdatedBy = UsersQuery.getCurrentUserData().getUser_Name();


        ///Dates
        LocalDate startDate = LocalDate.from(StartDatePicker.getValue());
        LocalDate endDate = LocalDate.from(EndDatePicker.getValue());



        //Times
        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        Timestamp startby = Timestamp.valueOf(start);
        Timestamp endby = Timestamp.valueOf(end);

        if (TimeUtil.businessHoursCheck(start, end) && (!TimeUtil.appointmentOverlapCheck(start, end, -1))) {//false
            AppointmentsQuery.insert(title, description, location, type, startby, endby, createDate, createdBy, lastUpdate, lastUpdatedBy, custid, userid, contact);

            DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
            String startformatDate = start.format(date_format);
            DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
            String startformatTime = start.format(time_format);
            String endformatTime = end.format(time_format);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(rb.getString("newApptAdd"));
            alert.setTitle(rb.getString("apptAdded1"));
            alert.setContentText(rb.getString("apptAdded2")+
                    "\n"+rb.getString("Title")+" : " + title +
                    "\n"+rb.getString("Date")+" : " + startformatDate +
                    "\n" + ZoneId.systemDefault() +"Time : " + startformatTime + " - " + endformatTime);
            alert.showAndWait();

            System.out.println("The appointment with the following criteria has been added : " +
                    "\nTitle : " + title +
                    "\nDate : " + startformatDate +
                    "\n" + ZoneId.systemDefault() + " Time : " + startformatTime + " - " + endformatTime);
            //apptScreenReturn(event);
        } else {
            Alerts.selectionWarning();
        }
    }


    @FXML
    public void apptScreenReturn(ActionEvent event) throws IOException {
        MainController.appointmentScreenButtonView(event);
    }


    /**
     * This function passes the id over to appointment screen
     * along with the customername in the adjacent combobox
     *
     */

    public void customer_Passer(int index, Customer customer) {
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
        CustomerComboBox.setValue(customer);
    }

    /**
     * upon selecting a contact, <b>contactUpdate</b> populates the contact id field with the correct value.
     * the below functions of <b>userUpdate</b> & <b>customerUpdate</b> are similar in nature and populate their
     * respective textfields with values selected from the comboboxes
     */
    public void contactUpdate() {
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));
    }

    public void userUpdate() {
        UserIDTxt.setText(String.valueOf(UserComboBox.getValue().getUser_ID()));
    }

    public void updateCustomerID() {
        CustomerIDTxt.setText(String.valueOf(CustomerComboBox.getValue().getCustomer_ID()));
    }

    public void updateCustomerName(){
       // CustomerComboBox.setValue();
        //int customerid = Integer.parseInt(CustomerIDTxt.getText());
        //CustomerComboBox.setValue();
        //CustomerComboBox.setValue(CustomerIDTxt.getText());
    }

    public String nextApptCheck() throws IOException{

        LocalTime nextAppt = LocalTime.now().plusMinutes(15);

        LocalTime currentTime = LocalTime.now();//Gives the current time in hour and minutes
        //break current time into hour and minutes hour must be equal or less than next hour
        int currenthour = LocalTime.now().getHour();
        //System.out.println(currenthour);

        // System.out.println(currentminute);
        //minutes be equal or less than 15 minute interval

        long timeDifference = ChronoUnit.MINUTES.between(currentTime,nextAppt);
        long interval = timeDifference;

        if(LocalTime.now().isBefore(LocalTime.of(currenthour,15)) && (interval>0 && interval <=15)){
            //timediff
            //return ("Next Appointment is at "+ LocalTime.of(currenthour,15));
            StartTimeCombo.setValue(LocalTime.of(currenthour,15));
            EndTimeCombo.setValue(LocalTime.of(currenthour,30));
        }
        else if(LocalTime.now().isBefore(LocalTime.of(currenthour,30)) && (interval>0 && interval <=15)){
            //timediff
            //return ("Next Appointment is at "+ LocalTime.of(currenthour,30));
            StartTimeCombo.setValue(LocalTime.of(currenthour,30));
            EndTimeCombo.setValue(LocalTime.of(currenthour,45));

        }else if(LocalTime.now().isBefore(LocalTime.of(currenthour,45)) && (interval>0 && interval <=15)){
            //timediff
            StartTimeCombo.setValue(LocalTime.of(currenthour,45));
            EndTimeCombo.setValue(LocalTime.of(currenthour+1,0));
        }
        else if(LocalTime.now().isBefore(LocalTime.of(currenthour+1,0)) && (interval>0 && interval <=15)){
            StartTimeCombo.setValue(LocalTime.of(currenthour+1,0));
            EndTimeCombo.setValue(LocalTime.of(currenthour+1,15));
        }
        return null;
    }
}
