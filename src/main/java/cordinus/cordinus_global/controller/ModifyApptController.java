package cordinus.cordinus_global.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

////https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class ModifyApptController {

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

    //•  All of the original appointment information is displayed on the update form in local time zone.
    //•  All of the appointment fields can be updated except Appointment_ID, which must be disabled.


    @FXML
    void UpdateAppt(ActionEvent event) {

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
