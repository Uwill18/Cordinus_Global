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
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


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
    private ComboBox<String> monthComboBox;

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

    @FXML
    private Label currentLbl;

    @FXML
    private Label remTitle;

    @FXML
    private Label remFifteen;
    @FXML
    private Label remThirty;
    @FXML
    private Label remFortyFive;
    @FXML
    private Label remSixty;


    @FXML
    private Tab apptRemTitleTab;

    @FXML
    private Tab apptTotalTitleTab;

    @FXML
    private Label contactSelectLbl;

    @FXML
    private Tab fullTab;

    @FXML
    private Label monthLbl;

    @FXML
    private Label totalsLbl;

    @FXML
    private Label typeLbl;
    @FXML
    private Button backBtn;


    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    //initialize
    /**https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/*/
    ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportTotalData = FXCollections.observableArrayList();
        reportContactData = FXCollections.observableArrayList();
        try {
            LoadReportTotals();
            LoadReportContacts();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        apptTotalTitleTab.setText(rb.getString("apptTotalTitle"));
        monthLbl.setText(rb.getString("Months"));
        typeLbl.setText(rb.getString("Type")+" : ");
        totalsLbl.setText(rb.getString("Totals"));
        totAppointmentID.setText(rb.getString("AppointmentID"));
        totAppointment_Type.setText(rb.getString("AppointmentType"));
        totDescription.setText(rb.getString("Description"));
        totCustomer_ID.setText(rb.getString("CustomerID"));
        totCustomer_Name.setText(rb.getString("CustomerName"));

        setCustomerAppointmentCellTable();

        fullTab.setText(rb.getString("Full"));
        contactSelectLbl.setText(rb.getString("contactSelect"));
        AllRB.setText(rb.getString("All"));
        WeekRB.setText(rb.getString("Week"));
        MonthRB.setText(rb.getString("Month"));
        AgendaRB.setText(rb.getString("Full"));
        Appointment_ID.setText(rb.getString("ApptID"));
        Title.setText(rb.getString("Title"));
        Description.setText(rb.getString("Description"));
        Type.setText(rb.getString("Type"));
        Start.setText(rb.getString("Start"));
        End.setText(rb.getString("End"));
        Contact_ID.setText(rb.getString("Contact")+" "+rb.getString("ID"));
        Customer_ID.setText(rb.getString("CustomerID"));

        setReportContactTable();
        ObservableList<Contact> allContacts = ContactsQuery.getAllContacts();
        ContactComboBox.setItems(allContacts);

        for (Appointment a : allAppointments) {
            if (!appointmentTypeList.contains(a.getType())) {
                appointmentTypeList.add(a.getType());
            }
            TypeComboBox.setItems(appointmentTypeList);
        }
/**https://stackoverflow.com/questions/44031561/get-month-name-from-month-number-for-a-series-of-numbers*/
        ObservableList<String> frenchMonths = FXCollections.observableArrayList();
        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            Month month = Month.of(monthNumber);// Get a month for number, 1-12 for January-December.
            String eachMonth = month.getDisplayName(TextStyle.FULL,Locale.getDefault()).toUpperCase(Locale.ROOT);
            frenchMonths.add(eachMonth);
        }

         monthComboBox.setItems(frenchMonths);
         monthComboBox.setVisibleRowCount(4);

        apptRemTitleTab.setText(rb.getString("apptRemTitle"));
        setCurrentDay();

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
        String formatDate = ldt.format(date_format);
        //currDateLbl.setText(formatDate);
        remTitle.setText(rb.getString("remTitle")+ formatDate);
        currentLbl.setText(rb.getString("CurrentDay")+" : ");


        remFifteen.setText(rb.getString("remFifteen"));
        remThirty.setText(rb.getString("remThirty"));
        remFortyFive.setText(rb.getString("remFortyFive"));
        remSixty.setText(rb.getString("remSixty"));

        remainingFifteen();
        remainingThirty();
        remainingFortyFive();
        remainingHours();

        backBtn.setText(rb.getString("backtoMain"));
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
        int month = monthComboBox.getSelectionModel().getSelectedIndex()+1;
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
        //ObservableList<Appointment> appointmentInfo = FXCollections.observableArrayList();
        ObservableList<Contact> getAllContacts = ContactsQuery.getAllContacts();

        Appointment contactAppointmentInfo;

        String contactName = String.valueOf(ContactComboBox.getSelectionModel().getSelectedItem());
/**Selects all appointments for all contacts*/
        if (AllRB.isSelected()){
            reportContactData = AppointmentsQuery.getAllAppointments();
        }

        if(MonthRB.isSelected()){
            contactIDFilter(contactID, getAllContacts, contactName);
        }
        else if(WeekRB.isSelected()) {
            contactIDFilter(contactID, getAllContacts, contactName);
        }

/**Gets all Appointments for a selected contact*/
        else if (AgendaRB.isSelected()){
            contactIDFilter(contactID, getAllContacts, contactName);
        }

        /**report data is added to the ReportTable in the view*/
        reportContactsTable.setItems(reportContactData);
    }

    /**this function was generated by IntelliJ, but I created the lambda expression which reduces
     *the number of comparisons executed for finalContactID as compared to the enhanced for loops
     * from before*/
    private void contactIDFilter(int contactID, ObservableList<Contact> getAllContacts, String contactName) {
        for (Contact contact: getAllContacts) {
            if (contactName.equals(contact.getContact_Name())) {
                contactID = contact.getContact_ID();
            }
        }
        int finalContactID = contactID;
        ObservableList<Appointment> appointmentInfo = AppointmentsQuery.getMonthAppointments().stream()
                .filter(appointment -> appointment.getContact_ID()== finalContactID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        reportContactData = appointmentInfo;
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
         * subtract the length of existing appointments while the length of the business day is greater than all appointments
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
            hourTxt.setText(rb.getString("remStatus1"));
        }

        if(localStart.isBefore(EST_BH_START)){
            hourTxt.setText(rb.getString("remStatus2"));
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
            quarterToTxt.setText(rb.getString("remStatus1"));
        }

        if(localStart.isBefore(EST_BH_START)){
            quarterToTxt.setText(rb.getString("remStatus2"));
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
            halfPastTxt.setText(rb.getString("remStatus1"));
        }

        if(localStart.isBefore(EST_BH_START)){
            halfPastTxt.setText(rb.getString("remStatus2"));
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
            quarterPastTxt.setText(rb.getString("remStatus1"));
        }

        if(localStart.isBefore(EST_BH_START)){
            quarterPastTxt.setText(rb.getString("remStatus2"));
        }
    }

    public void setCurrentDay(){
        DayOfWeek dow = LocalDate.now().getDayOfWeek();
        String currentDOW = dow.toString();

        if(currentDOW.equals("SUNDAY")){
            todayLbl.setText(rb.getString("Sunday"));
            //todayLbl.setText("Sunday");
        }

        if(currentDOW.equals("MONDAY")){
            todayLbl.setText(rb.getString("Monday"));
            //todayLbl.setText("Monday");
        }

        if(currentDOW.equals("TUESDAY")){
            todayLbl.setText(rb.getString("Tuesday"));
            //todayLbl.setText("Tuesday");
        }

        if(currentDOW.equals("WEDNESDAY")){
            todayLbl.setText(rb.getString("Wednesday"));
            //todayLbl.setText("Wednesday");
        }

        if(currentDOW.equals("THURSDAY")){
            todayLbl.setText(rb.getString("Thursday"));
            //todayLbl.setText("Thursday");
        }

        if(currentDOW.equals("FRIDAY")){
            todayLbl.setText(rb.getString("Friday"));
            //todayLbl.setText("Friday");
        }

        if(currentDOW.equals("SATURDAY")){
            todayLbl.setText(rb.getString("Saturday"));
            //todayLbl.setText("Saturday");
        }
    }



        @FXML
    public void mainMenuReturn(ActionEvent event) throws IOException {
            CustomerController.mainMenuReturnView(event);
        }


}
