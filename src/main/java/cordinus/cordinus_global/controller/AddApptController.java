package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
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

    private User user;


    @FXML
    void InsertAppt(ActionEvent event) throws SQLException,IOException {

//        String sql = "SELECT * FROM APPOINTMENTS ORDER BY APPOINTMENT_ID DESC LIMIT 1";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();


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

        //combobox is going to list local time objects
        //then insert times as timestamp, formatting not necessary
        // have the widget provide the info
        //db takes time as string and timestamp, timestamp preferred since it will convert to sql server timezone
        //if set to UTC

    //toDo:how to access the username variable
        String LastUpdatedBy="test";

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



        //todo add a business hours check and fix appointment id with appointmentOverlapCheck
        //todo exception handle for what is entered,or check for valid customer
        //todo or use combobox with only available entries from the db




//business hours check
        //create a method for startdatetime
        if(TimeUtil.businessHoursCheck(start, end) && TimeUtil.appointmentOverlapCheck(start,end)){
                AppointmentsQuery.insert(title, description, location, type, startby, endby, CreateDate,CreatedBy,LastUpdate, LastUpdatedBy,custid, userid,contact);
        }
//        else{
//            Alerts.selectionWarning();
//       }
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

        //Timing nested loop
        //Mr. Wabara reviewed timeloops with me to output to combobox
        //we discussed that combining the times with the date picker to a timestamp
        //would help format the time


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
//
//        for(Appointment appointment: AppointmentsQuery.getAllAppointments()){
//            TypeCombo.getItems().add(appointment.getType());
//        }


        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for(int i = 0; i<3; i++){
            TypeComboBox.getItems().add( (i+1) + " | "+ typeAppt[i]);
        }
        ContactComboBox.setItems(ContactsQuery.getAllContacts());
    }



    public void Customer_Passer(int index, Customer customer){
        this.customer = customer;
        this.index = index;

        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));//use same logic to setid for addapt and addcust
       // Customer_Name.setText(customer.getCustomer_Name());
        //ContactTxt.setText(  );



        //ContactTxt.setText((String.valueOf(customer.getCustomer_ID())));//toDo switch to Contact
        //UserIDTxt.setText((String.valueOf(customer.getCustomer_ID())));//toDO: switch to UserID
        //TitleTxt.setText(customer.getCustomer_Name() + "'s Appointment");
        //AddressTxt.setText(customer.getAddress());
        //Postal_Code.setText(customer.getPostal_Code());
        //Phone.setText(customer.getPhone());
        //Division_ID.setText((String.valueOf(customer.getDivision_ID())));

    }



    public void ContactUpdate(){
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));

    }
//    public ObservableList CheckFifteenMinutes(){
//        ObservableList AppointmentsFifteen = FXCollections.observableArrayList();
//        for(Appointment a: appointment){//get list of appts
//            if(a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(15))){
//                AppointmentsFifteen.add(a);
//            }
//        }
//        return AppointmentsFifteen;
//    }



}
