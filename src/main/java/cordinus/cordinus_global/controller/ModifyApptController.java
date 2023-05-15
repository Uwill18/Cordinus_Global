package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.DAO.CustomersQuery;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.*;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.utils.TimeUtil;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private ComboBox<User> UserComboBox;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ComboBox<String> TypeComboBox;
    private Appointment appointment;
    private int selectedIndex;
    private Customer customer;
    private int index;


    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

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
        UserComboBox.setItems(UsersQuery.getAllUsers());
        CustomerComboBox.setItems(CustomersQuery.getAllCustomers());
    }

    @FXML
    public void updateAppt(ActionEvent event) throws SQLException, IOException {
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contactid = ContactComboBox.getValue().getContact_ID();
        String type = TypeComboBox.getValue();
        Date date = new Date();//use LocalDateTime
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());
        Timestamp LastUpdate = timestamp;
        String LastUpdatedBy = UsersQuery.getCurrentUserData().getUser_Name();
        int customerid = Integer.parseInt(CustomerIDTxt.getText());//not mutable
        int userid = Integer.parseInt(UserIDTxt.getText());//control with exceptions

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


/**Applying the same logic as in AddApptController
 * appointment object
 * getting id
 * executes on !(TimeUtil.appointmentOverlapCheck(start, end, customerid, appointment.getAppointment_ID())), or true*/
        try {
            if(TimeUtil.businessHoursCheck(start, end) && !(TimeUtil.appointmentOverlapCheck(start, end, appointment.getAppointment_ID())) ){//Alerts.selectionWarning();
                 AppointmentsQuery.update(title, description, location, type, startby, endby, LastUpdate,LastUpdatedBy, userid ,contactid, customerid, appointment.getAppointment_ID());

                DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
                String startformatDate = start.format(date_format);
                DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
                String startformatTime = start.format(time_format);
                String endformatTime = end.format(time_format);

                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setHeaderText("Appointment Updated!");
                 //alert.setTitle(ZoneId.systemDefault() + "Timezone");
                 alert.setTitle("UPDATE CONFIRMATION");
                 alert.setContentText("The Appointment with the following Criteria has been updated :" +
                        "\nID# : "+ appointment.getAppointment_ID()  +
                        "\nDate : "+ startformatDate +
                        "\n"+ZoneId.systemDefault()+" Time : "+ startformatTime + " - " + endformatTime);
                 alert.showAndWait();

                System.out.println("The appointment with the following criteria has been added : " +
                        "\nTitle : "+ title  +
                        "\nDate : "+ startformatDate +
                        "\n"+ ZoneId.systemDefault()+" Time : "+ startformatTime + " - " + endformatTime);
                //System.out.println(AppointmentsQuery.);
                 apptScreenReturn(event);
            }
            else {
                Alerts.selectionWarning();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void apptScreenReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }



    public void appt_Passer(int selectedIndex, Appointment appointment){
        this.appointment = appointment;
        this.selectedIndex = selectedIndex;

        ApptIDTxt.setText(String.valueOf(appointment.getAppointment_ID()));
        TitleTxt.setText((String.valueOf(appointment.getTitle())));
        DescriptionTxt.setText(String.valueOf(appointment.getDescription()));
        LocationTxt.setText(String.valueOf(appointment.getLocation()));
        ContactTxt.setText(String.valueOf(appointment.getContact_ID()));
        ContactComboBox.setValue(AppointmentsQuery.getContactByID(appointment.getContact_ID())); //revisit for populating combo by id
        UserComboBox.setValue(AppointmentsQuery.getUserByID(Integer.parseInt(appointment.getUser_ID())));
        CustomerComboBox.setValue(AppointmentsQuery.getCustomerByID(appointment.getCustomer_ID()));
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

    public void customer_Passer(int index, Customer customer){
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
    }

    /**upon selecting a contact, this function populates the contact id field with the correct value*/
    public void contactUpdate(){
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));
    }

    public void userUpdate(){
        UserIDTxt.setText(String.valueOf(UserComboBox.getValue().getUser_ID()));
    }

    public void customerUpdate(){
        CustomerIDTxt.setText(String.valueOf(CustomerComboBox.getValue().getCustomer_ID()));
    }

}
