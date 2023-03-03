package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.appointment.AppointmentsList;
import cordinus.cordinus_global.helper.AppointmentsQuery;
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
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
//https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class AddApptController {


    @FXML
    private DatePicker EndDatePicker;

    @FXML
    private DatePicker StartDatePicker;


    public ComboBox<LocalTime> StartTimeCombo;

    public ComboBox<LocalTime> EndTimeCombo;

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
    void InsertAppt(ActionEvent event) throws SQLException {
        //String apptID = ApptIDTxt.getText();//disabled in this and modappt

        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contact = Integer.parseInt(ContactTxt.getText());
        String type = TypeTxt.getText();

        Date date = new Date();//use LocalDateTime
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

        Timestamp CreateDate = timestamp;
        Timestamp LastUpdate = timestamp;
        String CreatedBy ="test";

        //combobox is going to list local time objects
        //then insert times as timestamp, formatting not necessary
        // have the widget provide the info
        //db takes time as string and timestamp, timestamp preferred since it will convert to sql server timezone
        //if set to UTC



        String LastUpdatedBy ="test";


        ///Dates
        LocalDate startDate = LocalDate.from(StartDatePicker.getValue());
        Timestamp StartDateTimestamp = Timestamp.valueOf(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        LocalDate endDate = LocalDate.from(EndDatePicker.getValue());
        Timestamp EndDateTimestamp = Timestamp.valueOf(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));





        //Timing nested loop
        //Mr. Wabara reviewed timeloops with me to output to combobox
        //we discussed that combining the times with the date picker to a timestamp
        //would help format the time
        for(int h = 0; h < 24; h++){
            for(int  m = 0; m < 4; m++){ //<4 for 15m increments
                //startDate.getItems().add(LocalTime.of(i,0));
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

        //Times

        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        Timestamp timeStartTimestamp = Timestamp.valueOf(startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());
        Timestamp timeEndTimestamp = Timestamp.valueOf(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        //Timestamps

        Timestamp startby = Timestamp.valueOf(StartDateTimestamp+ " "+timeStartTimestamp);
        Timestamp endby = Timestamp.valueOf(EndDateTimestamp+" "+timeEndTimestamp);




        int custid = Integer.parseInt(CustomerIDTxt.getText());
        int userid = Integer.parseInt(UserIDTxt.getText());

        AppointmentsQuery.insert(title, description, location, type, startby, endby, CreateDate,CreatedBy,LastUpdate, LastUpdatedBy,contact, custid, userid);

        //cust id
        //user id

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
}
