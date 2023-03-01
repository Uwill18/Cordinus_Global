package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.helper.AppointmentsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
//https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class AddApptController {


    @FXML
    private DatePicker ApptEndPicker;



    @FXML
    private TextField ApptIDTxt;

    @FXML
    private DatePicker ApptStartPicker;

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
    void InsertAppt(ActionEvent event) throws SQLException {
        //String apptID = ApptIDTxt.getText();
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

        String LastUpdatedBy ="test";

        LocalDateTime myStartDT = LocalDateTime.from(ApptStartPicker.getValue());
        Timestamp FormattedStart = Timestamp.valueOf(myStartDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Timestamp startby = FormattedStart;

        LocalDateTime myEndDT = LocalDateTime.from(ApptEndPicker.getValue());
        Timestamp FormattedEnd = Timestamp.valueOf(myEndDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());
        Timestamp endby = FormattedEnd;

        AppointmentsQuery.insert(title, description, location, type, startby, endby, CreateDate,CreatedBy,LastUpdate, LastUpdatedBy,contact);



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
