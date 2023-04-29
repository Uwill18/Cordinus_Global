package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.DAO.DivisionsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.Contact;
import cordinus.cordinus_global.model.Division;
import cordinus.cordinus_global.reports.ReportsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;


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
    private TableColumn<?, ?> Contact_ID;

    @FXML
    private TableColumn<?, ?> Description;

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
    private TableColumn<?, ?>  Customer_Name;

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
    private TableColumn<?, ?> Appointment_Type;

    @FXML
    private ComboBox<?> MonthComboBox;

    @FXML
    private ComboBox<Appointment> TypeComboBox;

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

        ContactComboBox.setItems(ContactsQuery.getAllContacts());
       //ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
//        FilteredList<Appointment> selectedType = new FilteredList<>(allAppointments, i-> i.getAppointment_ID() == TypeComboBox.getSelectionModel().getSelectedItem().getAppointment_ID());
//        TypeComboBox.setItems(selectedType);
//        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();
//        for(Appointment a : AppointmentsQuery.getAllAppointments()){
//            a.getType().toString();
//            appointmentTypeList.add(a.getType());
//            TypeComboBox.setItems(appointmentTypeList.set(Appointment.appointment.getAppointment_ID(), AppointmentsQuery.getAllAppointments()));
//            //appointmentTypeList.stream().count();
//        }

    }


    //tab1 --total appts
    //the total number of customer appointments by type and month

    private void setCustomerAppointmentCellTable(){
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1
        //Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3
        //Location.setCellValueFactory(new PropertyValueFactory<>("Location"));//4,4
        //Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));//14, 5
        Appointment_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6
        //Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
       // End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8

        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        //User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
    }

    public void LoadReportTotals() throws SQLException {

        /**report data is added to the ReportTable in the view*/
        reportTotalData = AppointmentsQuery.getMonthAppointments();
        reportTotalsTable.setItems(reportTotalData);
    }



    //tab2 -- contact schedule
   //a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID




    private void setReportContactTable(){

        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1  x
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3  x
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));//4,4
        //Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));//14, 5
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6   x
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
        End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID")); //x
        //User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        //add customer_id
        //user_id

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


    public void OnRadioButton(ActionEvent event) throws SQLException {
        reportContactData.clear();
        LoadReportContacts();
    }




    //tab3 -- remaining appts/day
    //https://www.tutorialspoint.com/javafx/bar_chart.htm
    //https://docs.oracle.com/javafx/2/charts/bar-chart.htm
}
