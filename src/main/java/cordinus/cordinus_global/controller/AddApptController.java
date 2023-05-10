package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.*;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.utils.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.parse;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
//https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class AddApptController implements Initializable {
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private DatePicker StartDatePicker;
    public ComboBox<LocalTime> StartTimeCombo;
    public ComboBox<LocalTime> EndTimeCombo;
    @FXML
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private TextField ApptIDTxt;
    @FXML
    private TextField ContactTxt;
    @FXML
    private TextField DescriptionTxt;
    @FXML
    private TextField LocationTxt;
    @FXML
    private TextField TitleTxt;
    @FXML
    private TextField TypeTxt;
    @FXML
    private TextField CustomerIDTxt;
    @FXML
    private TextField UserIDTxt;

    @FXML
    private ComboBox<String> TypeComboBox;
    private Customer customer;
    private int index;



    @FXML
    void InsertAppt(ActionEvent event) throws SQLException,IOException {

        int userid = Integer.parseInt(UserIDTxt.getText());
        int custid = Integer.parseInt(CustomerIDTxt.getText());
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contact = ContactComboBox.getValue().getContact_ID();
        String type = TypeComboBox.getValue();

        LocalDateTime date =LocalDateTime.now();//use LocalDateTime
        Timestamp timestamp = Timestamp.valueOf(date);

        Timestamp CreateDate = timestamp;
        Timestamp LastUpdate = timestamp;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));

        String CreatedBy= "test";
        String LastUpdatedBy="test";

        /**Here my ComboBoxes list local time objects
        //then upon user selection, gets the value to be inserted for the times as a timestamp,
         //making timeformatting as a sting not necessary in this case.
        // The db takes said timestamp and converts it to sql server timezone
         //easier than it would a string, especially if it is set to UTC*/

        ///Dates
        LocalDate startDate = LocalDate.from(StartDatePicker.getValue());
        LocalDate endDate = LocalDate.from(EndDatePicker.getValue());

        //Times
        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());

        LocalDateTime start = LocalDateTime.of(startDate,startTime);
        LocalDateTime end = LocalDateTime.of(endDate,endTime);

         Timestamp startby = Timestamp.valueOf(start);
         Timestamp endby = Timestamp.valueOf(end);

        /**This is commented out now, but when testing my functions, this was valuable to evaluate the relationships
         * of output to input when configuring the boolean logic*/
        //System.out.println(TimeUtil.businessHoursCheck(start, end));
        //System.out.println(TimeUtil.appointmentOverlapCheck(start,end));


        /**Reviewed with Sunitha Kandalam the logic for verifying which appointments to insert.
         * Since this check is to verify IF something should be inserted, I thought a boolean
         * implementation similar to UsersQuery.userConfirmation(username,password) from my LoginController and the
         * CustomersQuery.deleteConfirmation(custID) in my  OnDeleteCustomer from the CustomerController would apply.
         * I learned to simplify, and to check when the appointmentOverlapCheck is not false to insert,
         * and otherwise alert if either of the boolean are false as below, as opposed to each time the functions
         * showed false.*/
        System.out.println("businessHoursCheck : " +(!TimeUtil.businessHoursCheck(start,end)));//true
        System.out.println("appointmentOverlapCheck : " +(TimeUtil.appointmentOverlapCheck(start,end)));//true

        //System.out.println("appointmentOverlapCheck : " +(!TimeUtil.appointmentOverlapCheck(start,end)));//false
        //apptOverlap must be false
        //where overlap occurs, return true, break
        //if true return overlap
        //else insert

        if(TimeUtil.businessHoursCheck(start, end) && (!TimeUtil.appointmentOverlapCheck(start,end))){//false
           AppointmentsQuery.insert(title, description, location, type, startby, endby, CreateDate,CreatedBy,LastUpdate, LastUpdatedBy,custid, userid,contact);
       }

        //false && false
        if(!TimeUtil.businessHoursCheck(start, end) || (TimeUtil.appointmentOverlapCheck(start,end))){
            Alerts.selectionWarning();
       }



        }


    @FXML
    void ApptScreenReturn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**Timing nested loop
        //Mr. Wabara reviewed timeloops with me to output to the combobox.
        //I learned that combining the times with the date picker to a timestamp
        //would help format the time, and this skill was transferrable as well to implementing
         the TypeComboBox as shown below, and the ContactComboBox.*/


        for(int h = 0; h < 23; h++){
            /**Intervals are hard coded for appt start-times*/
            StartTimeCombo.getItems().add(LocalTime.of(h,0));
            StartTimeCombo.getItems().add(LocalTime.of(h,15));
            StartTimeCombo.getItems().add(LocalTime.of(h,30));
            StartTimeCombo.getItems().add(LocalTime.of(h,45));
            /**Intervals are hard coded for appt end-times*/
            EndTimeCombo.getItems().add(LocalTime.of(h,0));
            EndTimeCombo.getItems().add(LocalTime.of(h,15));
            EndTimeCombo.getItems().add(LocalTime.of(h,30));
            EndTimeCombo.getItems().add(LocalTime.of(h,45));
        }
        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for(int i = 0; i<3; i++){
            TypeComboBox.getItems().add( (i+1) + " | "+ typeAppt[i]);
        }
        ContactComboBox.setItems(ContactsQuery.getAllContacts());
    }


    /**At this time, it simply passes the id over to appointment screen
     * I had planned to pass additional data, but that maybe applied when I
     * revisit this project*/

    public void Customer_Passer(int index, Customer customer){
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
    }


/**upon selecting a contact, this function populates the contact id field with the correct value*/
    public void ContactUpdate(){
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));
    }
}
