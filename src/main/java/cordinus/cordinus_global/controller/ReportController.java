package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;


//https://www.youtube.com/watch?v=KzfhgGGzWMQ
public class ReportController {
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


    //initialize
    public void initialize() throws SQLException, IOException {
        reportdata = FXCollections.observableArrayList();
        LoadReports();
        setReportCellTable();

    }


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

            String sql = "SELECT * FROM APPOINTMENTS ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String sqlmonth = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = month(now())";
            PreparedStatement mps = JDBC.connection.prepareStatement(sqlmonth);
            ResultSet mthrs = mps.executeQuery();


            String sqlweek = "SELECT * FROM APPOINTMENTS WHERE Start >= current_date() AND Start <= date_add(current_date(),interval 7 day)";
            PreparedStatement wkps = JDBC.connection.prepareStatement(sqlweek);
            ResultSet wkrs = wkps.executeQuery();

//                        while (rs.next()){

            //while(rs.next()){
            /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of Appointment
             * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

            // appointmentdata.add(new Appointment( rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(14), rs.getString(5),rs.getTimestamp(6).toLocalDateTime(),rs.getTimestamp(7).toLocalDateTime(), rs.getString(12), rs.getString(13)));

            if (AllRB.isSelected()){

                //appointmentdata.add(new Appointment( rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(14), rs.getString(5),rs.getTimestamp(6).toLocalDateTime(),rs.getTimestamp(7).toLocalDateTime(), rs.getString(12), rs.getString(13)));

                while(rs.next()){
                    /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of Appointment
                     * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

                    reportdata.add(new Appointment(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(14),
                            rs.getString(5),
                            rs.getTimestamp(6).toLocalDateTime(),
                            rs.getTimestamp(7).toLocalDateTime(),
                            rs.getString(12),
                            rs.getString(13)));

                }
            }
            if(MonthRB.isSelected()){
                while(mthrs.next()){
                    /**This line filters the above sql query for the radiobutton selection of months, and the according resultset is generated as done above*/

                    reportdata.add(new Appointment( mthrs.getInt(1),
                            mthrs.getString(2),
                            mthrs.getString(3),
                            mthrs.getString(4),
                            mthrs.getString(14),
                            mthrs.getString(5),
                            mthrs.getTimestamp(6).toLocalDateTime(),
                            mthrs.getTimestamp(7).toLocalDateTime(),
                            mthrs.getString(12),
                            mthrs.getString(13)));
                }
            }
            else if(WeekRB.isSelected()) {
                while(wkrs.next()){
                    /**This line filters the above sql query for the radiobutton selection of weeks, and the according resultset is generated as done above*/
                    reportdata.add(new Appointment(
                            wkrs.getInt(1),
                            wkrs.getString(2),
                            wkrs.getString(3),
                            wkrs.getString(4),
                            wkrs.getString(14),
                            wkrs.getString(5),
                            wkrs.getTimestamp(6).toLocalDateTime(),
                            wkrs.getTimestamp(7).toLocalDateTime(),
                            wkrs.getString(12),
                            wkrs.getString(13)));


                }
            }

        /**report data is added to the ReportTable in the view*/
        AppointmentTable.setItems(reportdata);
        System.out.println();
    }


    public void OnRadioButton(ActionEvent event) throws SQLException {
        reportdata.clear();
        LoadReports();
    }


    //tab3 -- remaining appts/day
    //https://www.tutorialspoint.com/javafx/bar_chart.htm
    //https://docs.oracle.com/javafx/2/charts/bar-chart.htm
}
