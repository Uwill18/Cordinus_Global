package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.appointment.AppointmentsList;
import cordinus.cordinus_global.helper.AppointmentsQuery;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

////https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class ModifyApptController implements Initializable {

    @FXML
    private DatePicker ApptEndPicker;

    @FXML
    private TextField ApptIDTxt;

    @FXML
    private DatePicker ApptStartPicker;

    public ComboBox<LocalTime> StartTimeCombo;

    public ComboBox<LocalTime> EndTimeCombo;

    @FXML
    private TextField ContactTxt;

    @FXML
    private TextField CustomerIDTxt;

    @FXML
    private TextField UserIDTxt;

    @FXML
    private TextField DescriptionTxt;

    @FXML
    private TextField LocationTxt;

    @FXML
    private TextField TitleTxt;

    @FXML
    private TextField TypeTxt;
    private AppointmentsList appointment;

    private int selectedIndex;

    //•  All of the original appointment information is displayed on the update form in local time zone.
    //•  All of the appointment fields can be updated except Appointment_ID, which must be disabled.



    @FXML
    public void UpdateAppt(ActionEvent event) throws SQLException {
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contactid = Integer.parseInt(ContactTxt.getText());
        String type = TypeTxt.getText();

        Date date = new Date();//use LocalDateTime
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

        Timestamp CreateDate = timestamp;
        Timestamp LastUpdate = timestamp;
        String CreatedBy ="test";

        String LastUpdatedBy ="test";

        int customerid = Integer.parseInt(CustomerIDTxt.getText());
        int userid = Integer.parseInt(UserIDTxt.getText());

        ///Dates
        LocalDate startDate = LocalDate.from(ApptStartPicker.getValue());



        LocalDate endDate = LocalDate.from(ApptEndPicker.getValue());







        //Times

        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());
        LocalDateTime start = LocalDateTime.of(startDate,startTime);
        LocalDateTime end = LocalDateTime.of(endDate,endTime);
        Timestamp startby = Timestamp.valueOf(start);
        Timestamp endby = Timestamp.valueOf(end);

        AppointmentsQuery.update(title, description, location, type, startby, endby, CreatedBy, LastUpdate,LastUpdatedBy, CreateDate,customerid, userid, contactid);


    }


    @FXML
    public void ApptScreenReturn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();

    }




    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Timing nested loop
        //Mr. Wabara reviewed timeloops with me to output to combobox
        //we discussed that combining the times with the date picker to a timestamp
        //would help format the time
        for(int hr = 8; hr < 17; hr++){
            StartTimeCombo.getItems().add(LocalTime.of(hr,0));
            StartTimeCombo.getItems().add(LocalTime.of(hr,15));
            StartTimeCombo.getItems().add(LocalTime.of(hr,30));
            StartTimeCombo.getItems().add(LocalTime.of(hr,45));
            EndTimeCombo.getItems().add(LocalTime.of(hr,0));
            EndTimeCombo.getItems().add(LocalTime.of(hr,15));
            EndTimeCombo.getItems().add(LocalTime.of(hr,30));
            EndTimeCombo.getItems().add(LocalTime.of(hr,45));
            //hardcode the times for forloops
        }

    }


    public void Appt_Passer(int selectedIndex, AppointmentsList appointment){
        this.appointment = appointment;
        this.selectedIndex = selectedIndex;



        TitleTxt.setText((String.valueOf(appointment.getTitle())));
        DescriptionTxt.setText(String.valueOf(appointment.getDescription()));
        LocationTxt.setText(String.valueOf(appointment.getLocation()));
        ContactTxt.setText(String.valueOf(appointment.getContact_ID()));
        TypeTxt.setText(String.valueOf(appointment.getType()));


        /**take the timestamp values, and set them to be passed**/
        ApptStartPicker.setValue(appointment.getStart().toLocalDate());
        ApptEndPicker.setValue(appointment.getEnd().toLocalDate());

        StartTimeCombo.setValue(appointment.getStart().toLocalTime());
        EndTimeCombo.setValue(appointment.getEnd().toLocalTime());



        System.out.println(appointment.getEnd());
        CustomerIDTxt.setText(String.valueOf(appointment.getCustomer_ID()));
        UserIDTxt.setText(String.valueOf(appointment.getUser_ID()));

    }




}
