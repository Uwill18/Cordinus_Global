package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import cordinus.cordinus_global.reports.ReportsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.Math.floor;


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


private ReportsInterface myReport = n -> {return n*n;};
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
        int x = myReport.calculateSquare(4);
        System.out.println("the square is : " + x);

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



        remainingFifteen();
        remainingThirty();
        remainingFortyFive();
        remainingHours();

    }



    //tab1 --total appts
    //the total number of customer appointments by type and month

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
    //tab2 -- contact schedule
   //a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID




    private void setReportContactTable(){
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1  x
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3  x
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6   x
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
        End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID")); //x
    }

    public void LoadReportContacts() throws SQLException {

        if (AllRB.isSelected()){
            reportContactData = AppointmentsQuery.getAllAppointments();
        }
        if(MonthRB.isSelected()){
            reportContactData = AppointmentsQuery.getMonthAppointments();
        }
        else if(WeekRB.isSelected()) {
            reportContactData = AppointmentsQuery.getWeekAppointments();
        }

        /**report data is added to the ReportTable in the view*/
        reportContactsTable.setItems(reportContactData);
    }

    public void ContactFilter(){

        int contactID = 0;
        ObservableList<Appointment> getAllAppointmentData = AppointmentsQuery.getAllAppointments();
        ObservableList<Appointment> appointmentInfo = FXCollections.observableArrayList();
        ObservableList<Contact> getAllContacts = ContactsQuery.getAllContacts();

        Appointment contactAppointmentInfo;

        String contactName = String.valueOf(ContactComboBox.getSelectionModel().getSelectedItem());

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
        reportContactsTable.setItems(appointmentInfo);
    }


    public void OnRadioButton(ActionEvent event) throws SQLException {
        reportContactData.clear();
        LoadReportContacts();
    }




    //tab3 -- remaining appts/day
    //https://www.tutorialspoint.com/javafx/bar_chart.htm
    //https://docs.oracle.com/javafx/2/charts/bar-chart.htm


//
//--get local time difference of a day
//--subtract duration of all appointment object for that day by getting the difference of end from start.
//            --subtract apptimediff from local time difference
//--divide remaining local time by relative time measurement
//-- return number to string +"/x"

    //        ObservableList<Appointment> dayAppt = AppointmentsQuery.getDayAppointments();
//        System.out.println(dayAppt.size());
//        int totalMinutes = 0;
//        int totalBusinessMinutes = 14*60;
//        int minutesLeft = 0;
//        for(Appointment a: dayAppt){
//            totalMinutes += ChronoUnit.MINUTES.between(a.getStart(),a.getEnd());
//
//        }
//        if(totalMinutes < totalBusinessMinutes){
//            minutesLeft = totalBusinessMinutes - totalMinutes;
//            //System.out.println(totalMinutes);
//            System.out.println("hours left: " + minutesLeft/60);
//            System.out.println("forty-five intervals left: " + minutesLeft/45);
//            System.out.println("thirty intervals left: " + minutesLeft/30);
//            System.out.println("fifteen minute intervals left: " + minutesLeft/15);
//
//        }
//        else{
//            System.out.println("no more appointments for the day");
//        }

    public void remainingHours(){
        //long businessDayMinutes = 840;
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
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
    }

    public void remainingFortyFive(){

        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
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

    }

    public void remainingThirty(){
        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
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

    }

    public void remainingFifteen(){

        final ZonedDateTime EST_BH_START = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"));
        ZonedDateTime localStart = EST_BH_START.withZoneSameInstant(ZoneId.systemDefault());
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
