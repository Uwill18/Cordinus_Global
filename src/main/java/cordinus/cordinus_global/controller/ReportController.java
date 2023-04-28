package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.reports.ReportsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;


//https://www.youtube.com/watch?v=KzfhgGGzWMQ
public  class ReportController implements Initializable {
    //FXML VARIABLES
    private ObservableList<Appointment> reportdata;


    @FXML
    private TableView<Appointment> AppointmentTable;

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
    private TableColumn<?, ?> Customer_ID;

    @FXML
    private TableColumn<?, ?> User_ID;

    @FXML
    private RadioButton AllRB;

    @FXML
    private RadioButton MonthRB;

    @FXML
    private RadioButton WeekRB;

private ReportsInterface myReport = n -> {return n*n;};
    //initialize



    //tab1 --total appts
    //the total number of customer appointments by type and month



    //tab2 -- contact schedule
   //a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID




    private void setReportCellTable(){

        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));//4,4
        //Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));//14, 5
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
        End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        //User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        //add customer_id
        //user_id

    }

    public void LoadReports() throws SQLException {

        if (AllRB.isSelected()){
            reportdata = AppointmentsQuery.getAllAppointments();
        }
        if(MonthRB.isSelected()){
            reportdata = AppointmentsQuery.getMonthAppointments();
        }
        else if(WeekRB.isSelected()) {
            reportdata = AppointmentsQuery.getWeekAppointments();
        }


        /**report data is added to the ReportTable in the view*/
        AppointmentTable.setItems(reportdata);
        System.out.println();
    }


    public void OnRadioButton(ActionEvent event) throws SQLException {
        reportdata.clear();
        LoadReports();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportdata = FXCollections.observableArrayList();
        try {
            LoadReports();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setReportCellTable();
        int x = myReport.calculateSquare(4);
        System.out.println("the square is : " + x);

    }


    //tab3 -- remaining appts/day
    //https://www.tutorialspoint.com/javafx/bar_chart.htm
    //https://docs.oracle.com/javafx/2/charts/bar-chart.htm
}
