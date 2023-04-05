package cordinus.cordinus_global.appointment;

import cordinus.cordinus_global.controller.MainController;
import cordinus.cordinus_global.controller.ModifyApptController;
import cordinus.cordinus_global.customer.CustomerController;
import cordinus.cordinus_global.helper.AppointmentsQuery;
import cordinus.cordinus_global.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//implement similar to Part
//initiate getters and setters
public class ApptController {



        private Connection con = null;//not sure if I need this...

        private PreparedStatement pst = null;//or this..
        //private ResultSet rs = null;
        private ObservableList<AppointmentsList> appointmentdata;


        @FXML
        private TableView<AppointmentsList> AppointmentTable;

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

        @FXML
        private Label ApptIntervalLbl;

        public void initialize() throws SQLException, IOException {
                appointmentdata = FXCollections.observableArrayList();
                LoadAppointments();
                setAppointmentCellTable();
                ApptIntervalLbl.setText(OnIntervalCheck().toString());
                //ApptIntervalLbl.setText(CheckFifteenMinutes().toString());
                //FifteenMinutesAlert();

        }


        private void setAppointmentCellTable(){

                Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));//1,1
                Title.setCellValueFactory(new PropertyValueFactory<>("Title"));//2,2
                Description.setCellValueFactory(new PropertyValueFactory<>("Description"));//3,3
                Location.setCellValueFactory(new PropertyValueFactory<>("Location"));//4,4
                Contact_ID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));//14, 5
                Type.setCellValueFactory(new PropertyValueFactory<>("Type"));//5,6
                Start.setCellValueFactory(new PropertyValueFactory<>("Start"));//6,7
                End.setCellValueFactory(new PropertyValueFactory<>("End"));//7,8
                Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
                User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

                //add customer_id
                //user_id

        }

        public void LoadAppointments() throws SQLException {

                try{


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
                                        /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of AppointmentsList
                                         * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

                                       // appointmentdata.add(new AppointmentsList( rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(14), rs.getString(5),rs.getTimestamp(6).toLocalDateTime(),rs.getTimestamp(7).toLocalDateTime(), rs.getString(12), rs.getString(13)));

                                        if (AllRB.isSelected()){

                                        //appointmentdata.add(new AppointmentsList( rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(14), rs.getString(5),rs.getTimestamp(6).toLocalDateTime(),rs.getTimestamp(7).toLocalDateTime(), rs.getString(12), rs.getString(13)));

                                        while(rs.next()){
                                                /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of AppointmentsList
                                                 * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

                                                appointmentdata.add(new AppointmentsList(
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

                                                        appointmentdata.add(new AppointmentsList( mthrs.getInt(1),
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
                                                        appointmentdata.add(new AppointmentsList(
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


                        //}




                } catch (SQLException e){
                        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE,null,e);
                }
                /**customer data is added to the CustomerTable in the view*/
                AppointmentTable.setItems(appointmentdata);
        }






        @FXML
        void OnAddAppt(ActionEvent event) throws IOException {

                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AddAppt.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Add Appointments");
                stage.setScene(scene);

                stage.show();

        }

        @FXML
        void OnDeleteAppt(ActionEvent event) throws SQLException{

                AppointmentsQuery.delete(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());

        }

        @FXML
        void OnUpdateAppt(ActionEvent event) throws IOException {

                try{
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModAppt.fxml"));
                        loader.load();
                        ModifyApptController modifyApptController = loader.getController();//get controller tied to view
                        modifyApptController.Appt_Passer(AppointmentTable.getSelectionModel().getSelectedIndex(),
                                AppointmentTable.getSelectionModel().getSelectedItem());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Modify Appointments");
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();

                }catch (NullPointerException e){

                        SelectionError();

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


        /**This radiobutton is responsible for Loading the different views according to the selection of each appointment
         * it is also used to refactor the appointmentdata.clear() method, which enables the view to be cleared every
         * time a new query is executed, so the new query results can be loaded to the view upon the selected condition*/
        public void OnRadioButton(ActionEvent event) throws SQLException {
                appointmentdata.clear();
                LoadAppointments();
        }

        /**
         * This function checks time intervals
         *
         * @return
         */
        public static String OnIntervalCheck() throws IOException{

                LocalTime nextAppt = LocalTime.now().plusMinutes(15);






                 LocalTime currentTime = LocalTime.now();
                 int currenthour = LocalTime.now().getHour();//fetches the current hour to compare for logic
                 long timeDifference = ChronoUnit.MINUTES.between(currentTime,nextAppt);
                 long interval = timeDifference;



//toDo: wrap a business hours check around the code
                //toDo : fetch UDT from Login and use it for LocalTime.now()
                //toDO: see if you can have the clock vary am and pm
                //toDo: see if you can make the app sign out after three suggested times e.g. 1 hour

                LocalTime BusinessStart = LocalTime.of(8,0);
                LocalTime BusinessEnd = LocalTime.of(22,0);

                if(!((LocalTime.now().isBefore(BusinessStart))||(LocalTime.now().isAfter(BusinessEnd)))){

                        if(LocalTime.now().isBefore(LocalTime.of(currenthour,15)) && (interval>0 && interval <=15)){
                                return ("The next appointment is at : "+ LocalTime.of(currenthour,15));
                        }
                        else if(LocalTime.now().isBefore(LocalTime.of(currenthour,30)) && (interval>0 && interval <=15)){
                                return ("The next appointment is at : "+ LocalTime.of(currenthour,30));

                        }else if(LocalTime.now().isBefore(LocalTime.of(currenthour,45)) && (interval>0 && interval <=15)){
                                return ("The next appointment is at : "+ LocalTime.of(currenthour,45));
                        }
                        else if(LocalTime.now().isBefore(LocalTime.of(currenthour+1,0)) && (interval>0 && interval <=15)){
                                return "The next appointment is at : "+ LocalTime.of(currenthour+1,0);
                        }
                        return null;

                }else{
                        return "There are no upcoming Appointments.";
                }


        }

        public ObservableList CheckFifteenMinutes(){
                ObservableList AppointmentsFifteen = FXCollections.observableArrayList();
                for(AppointmentsList a: appointmentdata){//get list of appts
                        if(a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(15))){
                             AppointmentsFifteen.add(a);
                        }
                }
            return AppointmentsFifteen;
        }


        public void FifteenMinutesAlert() throws SQLException, IOException {

                String sql = "SELECT * FROM APPOINTMENTS ORDER BY APPOINTMENT_ID DESC LIMIT 1";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();


                if(rs.next()){
/**This line filters the above sql string to select  data from specific columns, then sends them to an instance of AppointmentsList
 * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/


                        int x = rs.getInt(1);
//                        Date date = new Date();
//                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//                        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        String strDate = formatter.format(date);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Upcoming Appointment");
                        alert.setTitle("Appointment Notification");
                        alert.setContentText("Hello!\nToday is : " + strDate + "  \n"+OnIntervalCheck().toString()+"\nAppointment ID#: " + (x + 1));
                        alert.showAndWait();



                }




        }


        public static void SelectionError(){


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SELECTION ERROR");
                alert.setContentText("No selection was made for this operation.");
                alert.showAndWait();

        }
}
