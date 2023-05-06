package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import cordinus.cordinus_global.model.Customer;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.utils.TimeUtil;
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
    @FXML
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private ComboBox<String> TypeComboBox;
    private Appointment appointment;
    private int selectedIndex;
    private Customer customer;
    private int index;

    @FXML
    public void UpdateAppt(ActionEvent event) throws SQLException, IOException {
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contactid = ContactComboBox.getValue().getContact_ID();
        String type = TypeComboBox.getValue();
        Date date = new Date();//use LocalDateTime
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());
        Timestamp CreateDate = timestamp;
        Timestamp LastUpdate = timestamp;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));
        loader.load();
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

/**Applying the same logic as in AddApptController*/
        if(TimeUtil.businessHoursCheck(start, end) && !(TimeUtil.appointmentOverlapCheck(start,end))){//Alerts.selectionWarning();
            AppointmentsQuery.update(title, description, location, type, startby, endby,CreateDate, CreatedBy, LastUpdate,LastUpdatedBy, userid ,contactid, customerid);
        }

        if(!TimeUtil.businessHoursCheck(start, end) || (TimeUtil.appointmentOverlapCheck(start,end))){
            Alerts.selectionWarning();
        }

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
        for(int hr = 8; hr < 22; hr++){
            StartTimeCombo.getItems().add(LocalTime.of(hr,0));
            StartTimeCombo.getItems().add(LocalTime.of(hr,15));
            StartTimeCombo.getItems().add(LocalTime.of(hr,30));
            StartTimeCombo.getItems().add(LocalTime.of(hr,45));
            EndTimeCombo.getItems().add(LocalTime.of(hr,0));
            EndTimeCombo.getItems().add(LocalTime.of(hr,15));
            EndTimeCombo.getItems().add(LocalTime.of(hr,30));
            EndTimeCombo.getItems().add(LocalTime.of(hr,45));
        }
        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for(int i = 0; i<3; i++){
            TypeComboBox.getItems().add( (i+1) + " | "+ typeAppt[i]);
        }
        ContactComboBox.setItems(ContactsQuery.getAllContacts());
    }


    public void Appt_Passer(int selectedIndex, Appointment appointment){
        this.appointment = appointment;
        this.selectedIndex = selectedIndex;

        ApptIDTxt.setText(String.valueOf(appointment.getAppointment_ID()));
        TitleTxt.setText((String.valueOf(appointment.getTitle())));
        DescriptionTxt.setText(String.valueOf(appointment.getDescription()));
        LocationTxt.setText(String.valueOf(appointment.getLocation()));
        ContactTxt.setText(String.valueOf(appointment.getContact_ID()));
        ContactComboBox.setValue(appointment.getContact().get(appointment.getContact_ID()));//gets index minus to set current contactcombo
        TypeComboBox.setValue(String.valueOf(appointment.getType()));
        UserIDTxt.setText(String.valueOf(appointment.getUser_ID()));

        /**These sets of pickers take the Local Date, and LocalTime values,
         * then they set them as timestamp values to be passed back to the view**/
        ApptStartPicker.setValue(appointment.getStart().toLocalDate());
        ApptEndPicker.setValue(appointment.getEnd().toLocalDate());
        StartTimeCombo.setValue(appointment.getStart().toLocalTime());
        EndTimeCombo.setValue(appointment.getEnd().toLocalTime());
        CustomerIDTxt.setText(String.valueOf(appointment.getCustomer_ID()));
        UserIDTxt.setText(String.valueOf(appointment.getUser_ID()));
    }

    public void Customer_Passer(int index, Customer customer){
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
    }

}
