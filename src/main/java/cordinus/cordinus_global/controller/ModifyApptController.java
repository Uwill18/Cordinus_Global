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
import javafx.scene.control.*;
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
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private ComboBox<User> UserComboBox;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ComboBox<String> TypeComboBox;
    @FXML
    private Label typeLbl;
    @FXML
    private Label startTimeLbl;
    @FXML
    private Label endTimeLbl;
    @FXML
    private Label appointmentIDLbl;
    @FXML
    private TextField ContactTxt;
    @FXML
    private Label contactLbl;
    @FXML
    private TextField CustomerIDTxt;
    @FXML
    private Label customerIDLbl;
    @FXML
    private TextField UserIDTxt;
    @FXML
    private Label userIDLbl;
    @FXML
    private TextField DescriptionTxt;
    @FXML
    private Label descriptionLbl;
    @FXML
    private TextField LocationTxt;
    @FXML
    private Label locationLbl;

    @FXML
    private TextField TitleTxt;
    @FXML
    private Label titleLbl;
    @FXML
    private Button modBtn;
    @FXML
    private Button backBtn;

    private Appointment appointment;
    private int selectedIndex;
    private Customer customer;
    private int index;


    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int hr = 8; hr < 22; hr++) {
            AddApptController.getStartTimeCombo(hr, StartTimeCombo, EndTimeCombo);
        }
        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for (int i = 0; i < 3; i++) {
            TypeComboBox.getItems().add((i + 1) + " | " + typeAppt[i]);
        }
        ContactComboBox.setItems(ContactsQuery.getAllContacts());
        UserComboBox.setItems(UsersQuery.getAllUsers());
        CustomerComboBox.setItems(CustomersQuery.getAllCustomers());

        /////////////////////////////////

        appointmentIDLbl.setText(rb.getString("AppointmentID"));
        userIDLbl.setText(rb.getString("UserID"));
        customerIDLbl.setText(rb.getString("CustomerID"));
        titleLbl.setText(rb.getString("Title"));
        descriptionLbl.setText(rb.getString("Description"));
        locationLbl.setText(rb.getString("Location"));
        startTimeLbl.setText(rb.getString("Start"));
        endTimeLbl.setText(rb.getString("End"));
        contactLbl.setText(rb.getString("Contact")+" "+rb.getString("ID") +" & "+rb.getString("Name"));
        typeLbl.setText(rb.getString("Type"));

        modBtn.setText(rb.getString("Modify"));
        backBtn.setText(rb.getString("Back"));






        /////////////////////////

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
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date));
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
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        Timestamp startby = Timestamp.valueOf(start);
        Timestamp endby = Timestamp.valueOf(end);


/**Applying the same logic as in AddApptController
 * appointment object
 * getting id
 * executes on !(TimeUtil.appointmentOverlapCheck(start, end, customerid, appointment.getAppointment_ID())), or true*/
        try {
            if (TimeUtil.businessHoursCheck(start, end) && !(TimeUtil.appointmentOverlapCheck(start, end, appointment.getAppointment_ID()))) {//Alerts.selectionWarning();
                AppointmentsQuery.update(title, description, location, type, startby, endby, LastUpdate, LastUpdatedBy, userid, contactid, customerid, appointment.getAppointment_ID());

                DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
                String startformatDate = start.format(date_format);
                DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
                String startformatTime = start.format(time_format);
                String endformatTime = end.format(time_format);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("curApptMod"));
                alert.setTitle(rb.getString("apptUpdate1"));
                alert.setContentText(rb.getString("apptUpdated2")+
                        "\n"+rb.getString("APPOINTMENTID")+": " + appointment.getAppointment_ID() +
                        "\n"+rb.getString("Date")+" : " + startformatDate +
                        "\n" + ZoneId.systemDefault() + " Time : " + startformatTime + " - " + endformatTime);
                alert.showAndWait();

                System.out.println("The appointment with the following criteria has been added : " +
                        "\nTitle : " + title +
                        "\nDate : " + startformatDate +
                        "\n" + ZoneId.systemDefault() + " Time : " + startformatTime + " - " + endformatTime);
                apptScreenReturn(event);
            } else {
                Alerts.selectionWarning();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void apptScreenReturn(ActionEvent event) throws IOException {
        MainController.appointmentScreenButtonView(event);
    }


    public void appt_Passer(int selectedIndex, Appointment appointment) {
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


        ApptStartPicker.setValue(appointment.getStart().toLocalDate());
        ApptEndPicker.setValue(appointment.getEnd().toLocalDate());
        StartTimeCombo.setValue(appointment.getStart().toLocalTime());
        EndTimeCombo.setValue(appointment.getEnd().toLocalTime());
        CustomerIDTxt.setText(String.valueOf(appointment.getCustomer_ID()));
        UserIDTxt.setText(String.valueOf(appointment.getUser_ID()));
    }

    public void customer_Passer(int index, Customer customer) {
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
    }

    /**
     * upon selecting a contact, this function populates the contact id field with the correct value
     */
    public void contactUpdate() {
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));
    }

    public void userUpdate() {
        UserIDTxt.setText(String.valueOf(UserComboBox.getValue().getUser_ID()));
    }

    public void customerUpdate() {
        CustomerIDTxt.setText(String.valueOf(CustomerComboBox.getValue().getCustomer_ID()));
    }

}
