package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.appointment.AppointmentsList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

////https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class ModifyApptController implements HoursLoop {

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

    //•  All of the original appointment information is displayed on the update form in local time zone.
    //•  All of the appointment fields can be updated except Appointment_ID, which must be disabled.


    @Override
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


    }

    @Override
    @FXML
    public void ApptScreenReturn(ActionEvent event) throws IOException {

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
        for(int h = 8; h < 17; h++){
            StartTimeCombo.getItems().add(LocalTime.of(h,0));
            StartTimeCombo.getItems().add(LocalTime.of(h,15));
            StartTimeCombo.getItems().add(LocalTime.of(h,30));
            StartTimeCombo.getItems().add(LocalTime.of(h,45));
            EndTimeCombo.getItems().add(LocalTime.of(h,0));
            EndTimeCombo.getItems().add(LocalTime.of(h,15));
            EndTimeCombo.getItems().add(LocalTime.of(h,30));
            EndTimeCombo.getItems().add(LocalTime.of(h,45));
            //hardcode the times for forloops
        }

    }


    public void Appt_Passer(AppointmentsList appointment){

        TitleTxt.setText((String.valueOf(appointment.getTitle())));
        DescriptionTxt.setText(String.valueOf(appointment.getDescription()));
        LocationTxt.setText(String.valueOf(appointment.getLocation()));
        ContactTxt.setText(String.valueOf(appointment.getContact_ID()));
        TypeTxt.setText(String.valueOf(appointment.getType()));

        CustomerIDTxt.setText(String.valueOf(appointment.getCustomer_ID()));
        UserIDTxt.setText(String.valueOf(appointment.getUser_ID()));

    }




}
