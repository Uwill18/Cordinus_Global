package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


//https://www.youtube.com/watch?v=KzfhgGGzWMQ
public  class ReportController implements Initializable {
    //FXML VARIABLES
    private ObservableList<Appointment> reportContactData;
    private ObservableList<Appointment> reportTotalData;
    @FXML
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private TableView<Appointment> reportContactsTable;
    @FXML
    private TableView<Appointment> reportTotalsTable;
    @FXML
    private TableColumn<?, ?> Appointment_ID;
    @FXML
    private TableColumn<?, ?> totAppointmentID;
    @FXML
    private TableColumn<?, ?> Contact_ID;
    @FXML
    private TableColumn<?, ?> Description;
    @FXML
    private TableColumn<?, ?> totDescription;

    @FXML
    private TableColumn<?, ?> End;

    @FXML
    private TableColumn<?, ?> Location;

    @FXML
    private TableColumn<?, ?> Start;

    @FXML
    private TableColumn<?, ?> Title;

    @FXML
    private TableColumn<?, ?> Type;

    @FXML
    private TableColumn<?, ?>  totCustomer_Name;

    @FXML
    private TableColumn<?, ?> totCustomer_ID;

    @FXML
    private TableColumn<?, ?> Customer_ID;

    @FXML
    private TableColumn<?, ?> User_ID;

    @FXML
    private RadioButton AllRB;

    @FXML
    private RadioButton MonthRB;

    @FXML
    private RadioButton WeekRB;

    @FXML
    private RadioButton AgendaRB;

    @FXML
    private TableColumn<?, ?> totAppointment_Type;

    @FXML
    private ComboBox<Month> MonthComboBox;

    @FXML
    private ComboBox<String> TypeComboBox;

    @FXML
    private TextField apptTotals;

    @FXML
    private TextField halfPastTxt;

    @FXML
    private TextField hourTxt;

    @FXML
    private TextField quarterPastTxt;

    @FXML
    private TextField quarterToTxt;

    @FXML
    private Label todayLbl;

    @FXML
    private Label currDateLbl;

    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    //initialize
    /**https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/*/
    ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportContactData = FXCollections.observableArrayList();
        reportTotalData = FXCollections.observableArrayList();
        try {
            LoadReportTotals();
            LoadReportContacts();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setReportContactTable();
        setCustomerAppointmentCellTable();

        ObservableList<Contact> allContacts = ContactsQuery.getAllContacts();
        ContactComboBox.setItems(allContacts);

        for (Appointment a : allAppointments) {
            if (!appointmentTypeList.contains(a.getType())) {
                appointmentTypeList.add(a.getType());
            }
            TypeComboBox.setItems(appointmentTypeList);
        }
/**https://stackoverflow.com/questions/44031561/get-month-name-from-month-number-for-a-series-of-numbers*/
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            Locale.getDefault();
            Month month = Month.of(monthNumber);  // Get a month for number, 1-12 for January-December.
            allMonths.add(Month.valueOf(String.valueOf(month)));
        }
        MonthComboBox.setItems(allMonths);
        MonthComboBox.setVisibleRowCount(4);

        DayOfWeek dow = LocalDate.now().getDayOfWeek();
        String currentDOW = dow.toString();
        todayLbl.setText(currentDOW);

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
        String formatDate = ldt.format(date_format);
        currDateLbl.setText(formatDate);

        remainingFifteen();
        remainingThirty();
        remainingFortyFive();
        remainingHours();
    }



    /**TAB1 -- Total number of customer appointments by type and month*/

    private void setCustomerAppointmentCellTable(){
        totAppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1
        totDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3
        totAppointment_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6
        totCustomer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        totCustomer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
    }

    public void LoadReportTotals() throws SQLException {
        /**report data is added to the ReportTable in the view*/
        reportTotalData = AppointmentsQuery.getMonthAppointments();
        reportTotalsTable.setItems(reportTotalData);
    }

/**sets the result Textfield by obtaining the value of the ComboBoxes
 * passing them to AppointmentsQuery.getAppointmentByTypeMonth,
 * and storing them in the result integer.
 * upon each selection the reportsTotalData collection is reset
 * and has the values per the AppointmentsQuery.getTotalAppointments reassigned
 * to it. The reportsTotalTable is then set according to reportTotalData*/
    public void onActionFilter(ActionEvent event) throws SQLException {
        String type = TypeComboBox.getValue();
        int month = MonthComboBox.getValue().getValue();
        int result = AppointmentsQuery.getAppointmentByTypeMonth(type, month);
        apptTotals.setText(String.valueOf(result));
        reportTotalData.clear();
        reportTotalData = AppointmentsQuery.getTotalAppointments(type,month);
        reportTotalsTable.setItems(reportTotalData);
    }



/**TAB II -- CONTACT SCHEDULE*/

    private void setReportContactTable(){
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1  x
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3  x
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6   x
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
        End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID")); //x
        Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
    }

    /**Used Radiobuttons to filter selection of contacts according to appointments per the selected time range*/
    public void LoadReportContacts() throws SQLException {

        int contactID = 0;
        ObservableList<Appointment> getAllAppointmentData = AppointmentsQuery.getAllAppointments();
        ObservableList<Appointment> appointmentInfo = FXCollections.observableArrayList();
        ObservableList<Contact> getAllContacts = ContactsQuery.getAllContacts();

        Appointment contactAppointmentInfo;

        String contactName = String.valueOf(ContactComboBox.getSelectionModel().getSelectedItem());
/**Selects all appointments for all contacts*/
        if (AllRB.isSelected()){
            reportContactData = AppointmentsQuery.getAllAppointments();
        }


        if(MonthRB.isSelected()){
            for (Contact contact: getAllContacts) {
                if (contactName.equals(contact.getContact_Name())) {
                    contactID = contact.getContact_ID();
                }
            }

            for (Appointment appointment: AppointmentsQuery.getMonthAppointments()) {
                if (appointment.getContact_ID() == contactID) {
                    contactAppointmentInfo = appointment;
                    appointmentInfo.add(contactAppointmentInfo);
                }
            }
            reportContactData = appointmentInfo;
        }
        else if(WeekRB.isSelected()) {

            for (Contact contact: getAllContacts) {
                if (contactName.equals(contact.getContact_Name())) {
                    contactID = contact.getContact_ID();
                }
            }


            for (Appointment appointment: AppointmentsQuery.getWeekAppointments()) {
                if (appointment.getContact_ID() == contactID) {
                    contactAppointmentInfo = appointment;
                    appointmentInfo.add(contactAppointmentInfo);
                }
            }

            reportContactData = appointmentInfo;
        }

/**Gets all Appointments for a selected contact*/
        else if (AgendaRB.isSelected()){
            for (Contact contact: getAllContacts) {
                if (contactName.equals(contact.getContact_Name())) {
                    contactID = contact.getContact_ID();
                }
            }
            for (Appointment appointment: getAllAppointmentData) {
                if (appointment.getContact_ID() == contactID) {
                    contactAppointmentInfo = appointment;
                    appointmentInfo.add(contactAppointmentInfo);
                }
            }
            reportContactData = appointmentInfo;
        }

        /**report data is added to the ReportTable in the view*/
        reportContactsTable.setItems(reportContactData);
    }




    public void OnRadioButton(ActionEvent event) throws SQLException {
        reportContactData.clear();
        LoadReportContacts();
    }




    /**TAB3 -- remaining appts/day*/



    public void remainingHours(){
        /**While I could simply calculate the difference of appointments remaining by subtracting the
         * time difference of appointments booked from the total length of a business day in minutes, being 840,
         * it was important to me that this function factor in that past appointments cannot be booked, and catering
         * to a length of time starting at the beginning of the day does not take that into consideration.
         * So.. in this function I find the difference between the LocalTime of now and the end of BusinessDay,
         * subtract the length of existing appointments while the length of the businessday is greater than all appointments
         * and divide by the time constant. Otherwise, if there are no appointments,
         * it will simply divide the remaining time by the time constant, and output No remaining appointments available after business end
         * if it is before business start it will output Available bookings will display at 8:00 a.m.
         * Remaining appointments are also displayed according to system timezone of the user's device and from the sql server.*/

        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        final ZonedDateTime EST_CURRENT_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_CURRENT_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        long businessDayMinutes = ChronoUnit.MINUTES.between(localStart,localEnd);
        long appointmentTimeSum = AppointmentsQuery.getTimeSum();
        if(appointmentTimeSum<businessDayMinutes){
            long timeTotal = (businessDayMinutes- appointmentTimeSum)/60;
            hourTxt.setText(String.valueOf(timeTotal));
        }else{
            long timeTotal = (businessDayMinutes)/60;
            hourTxt.setText(String.valueOf(timeTotal));
        }

        if(businessDayMinutes<0){
            hourTxt.setText("No remaining appointments available.");
        }

        if(localStart.isBefore(EST_BH_START)){
            hourTxt.setText("Available bookings will display at 8:00 a.m.");
        }
    }

    public void remainingFortyFive(){

        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        final ZonedDateTime EST_CURRENT_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_CURRENT_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        long businessDayMinutes = ChronoUnit.MINUTES.between(localStart,localEnd);
        long appointmentTimeSum = AppointmentsQuery.getTimeSum();
        if(appointmentTimeSum < businessDayMinutes){
            long timeTotal = (businessDayMinutes- appointmentTimeSum)/45;
            quarterToTxt.setText(String.valueOf(timeTotal));
        }else{
            long timeTotal = (businessDayMinutes)/45;
            quarterToTxt.setText(String.valueOf(timeTotal));
        }

        if(businessDayMinutes<=0){
            quarterToTxt.setText("No remaining appointments available.");
        }

        if(localStart.isBefore(EST_BH_START)){
            quarterToTxt.setText("Available bookings will display at 8:00 a.m.");
        }

    }

    public void remainingThirty(){
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        final ZonedDateTime EST_CURRENT_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_CURRENT_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        long businessDayMinutes = ChronoUnit.MINUTES.between(localStart,localEnd);
        long appointmentTimeSum = AppointmentsQuery.getTimeSum();
        if(appointmentTimeSum < businessDayMinutes){
            long timeTotal = (businessDayMinutes- appointmentTimeSum)/30;
            halfPastTxt.setText(String.valueOf(timeTotal));
        }else{
            long timeTotal = (businessDayMinutes)/30;
            halfPastTxt.setText(String.valueOf(timeTotal));
        }

        if(businessDayMinutes<=0){
            halfPastTxt.setText("No remaining appointments available.");
        }

        if(localStart.isBefore(EST_BH_START)){
            halfPastTxt.setText("Available bookings will display at 8:00 a.m.");
        }
    }

    public void remainingFifteen(){
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        final ZonedDateTime EST_CURRENT_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_CURRENT_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        long businessDayMinutes = ChronoUnit.MINUTES.between(localStart,localEnd);
        long appointmentTimeSum = AppointmentsQuery.getTimeSum();
        if(appointmentTimeSum < businessDayMinutes){
            long timeTotal = (businessDayMinutes- appointmentTimeSum)/15;
            quarterPastTxt.setText(String.valueOf(timeTotal));
        }else{
            long timeTotal = (businessDayMinutes)/15;
            quarterPastTxt.setText(String.valueOf(timeTotal));
        }

        if(businessDayMinutes<=0){
            quarterPastTxt.setText("No remaining appointments available.");
        }

        if(localStart.isBefore(EST_BH_START)){
            quarterPastTxt.setText("Available bookings will display at 8:00 a.m.");
        }
    }





        @FXML
    void MainMenuReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}
