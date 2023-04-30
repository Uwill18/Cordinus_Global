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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.format.TextStyle;
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
    private TableColumn<?, ?> totAppointment_Type;

    @FXML
    private ComboBox<Month> MonthComboBox;

    @FXML
    private ComboBox<String> TypeComboBox;

    @FXML
    private TextField apptTotals;

private ReportsInterface myReport = n -> {return n*n;};
    //initialize
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


        /**https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/*/
        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        for(Appointment a : allAppointments){
            if(!appointmentTypeList.contains(a.getType())) {
                appointmentTypeList.add(a.getType());
            }
            TypeComboBox.setItems(appointmentTypeList);
            }
/**https://stackoverflow.com/questions/44031561/get-month-name-from-month-number-for-a-series-of-numbers*/
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        for ( int monthNumber = 1; monthNumber <= 12; monthNumber++ ) {
            Locale.getDefault();
            Month month = Month.of( monthNumber );  // Get a month for number, 1-12 for January-December.
            allMonths.add(Month.valueOf(String.valueOf(month)));
        }
        MonthComboBox.setItems(allMonths);
        MonthComboBox.setVisibleRowCount(4);


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

    public void typeFilter(){

        ContactComboBox.setItems(ContactsQuery.getAllContacts());
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
         //FilteredList<Appointment> selectedType = new FilteredList<>(allAppointments, i-> i.getAppointmentType().equals(TypeComboBox.getSelectionModel().getSelectedItem().getAppointmentType()));
         //TypeComboBox.setItems(selectedType);
        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
        for(Appointment a : AppointmentsQuery.getAllAppointments()) {
            a.getAppointmentType();

            //appointmentTypeList.add(a.getType());

            //TypeComboBox.setItems(appointmentTypeList);
            //TypeComboBox.setItems(appointmentTypeList.set(Appointment.appointment.getAppointment_ID(), AppointmentsQuery.getAllAppointments()));
            // appointmentTypeList.stream().count();
            /***/

            Appointment appointment = null;
            TypeComboBox.setVisibleRowCount(3);
            TypeComboBox.getSelectionModel().selectFirst();
        }

    }

    public void monthFilter(){

    }



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
}
