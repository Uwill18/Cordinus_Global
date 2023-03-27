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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
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

        LocalDateTime date =LocalDateTime.now();//use LocalDateTime
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());
        Timestamp timestamp = Timestamp.valueOf(date);

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



        LocalDate endDate = LocalDate.from(EndDatePicker.getValue());







        //Times

        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());
        LocalDateTime start = LocalDateTime.of(startDate,startTime);
        LocalDateTime end = LocalDateTime.of(endDate,endTime);
        Timestamp startby = Timestamp.valueOf(start);
        Timestamp endby = Timestamp.valueOf(end);

//        ChronoUnit monthopt = ChronoUnit.MONTHS.toString();
//        Calendar.getInstance().get(Calendar.MONTH);


        int custid = Integer.parseInt(CustomerIDTxt.getText());
        int userid = Integer.parseInt(UserIDTxt.getText());

        //todo add a business hours check
        //todo exception handle for what is entered,or check for valid customer
        //todo or use combobox with only available entries from the db
        //todo add a third toggle for all appts





        AppointmentsQuery.insert(title, description, location, type, startby, endby, CreateDate,CreatedBy,LastUpdate, LastUpdatedBy,custid, userid,contact);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Timing nested loop
        //Mr. Wabara reviewed timeloops with me to output to combobox
        //we discussed that combining the times with the date picker to a timestamp
        //would help format the time
        for(int h = 8; h < 22; h++){
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
}
