package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.utils.TimeUtil;
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

//implement similar to Part
//initiate getters and setters
public class ApptController {

        private ObservableList<Appointment> appointmentdata;


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

        @FXML
        private Label ApptIntervalLbl;
        
        public Appointment appointment;

        public void initialize() throws SQLException, IOException {
                appointmentdata = FXCollections.observableArrayList();
                LoadAppointments();
                setAppointmentCellTable();

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
                                        /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of Appointment
                                         * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

                                        if (AllRB.isSelected()){


                                        while(rs.next()){
                                                /**This line filters the above sql string to select  data from specific columns, then sends them to an instance of Appointment
                                                 * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/

                                                appointmentdata.add(new Appointment(
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

                                                        appointmentdata.add(new Appointment( mthrs.getInt(1),
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
                                                        appointmentdata.add(new Appointment(
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
                //toDO if selected alert, else if nothing selected.. selection error

                if((AppointmentTable.getSelectionModel().getSelectedItem() != null)){

                        try{
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Delete Warning");
                                        alert.setHeaderText("Deleting Appointment");
                                        alert.setContentText("Are you sure you wish to delete this appointment? "
                                                + "\nAPPOINTMENT ID# : " + AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID()
                                        +"\nAPPOINTMENT TYPE :" + AppointmentTable.getSelectionModel().getSelectedItem().getType());
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if(result.isPresent()  && result.get() != ButtonType.CANCEL){
                                                if(result.isPresent()  && result.get() ==ButtonType.OK){
                                                        Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                        delete_alert.setTitle("Delete Confirmation");
                                                        delete_alert.setHeaderText("Appointment Deleted");
                                                        delete_alert.setContentText("THE FOLLOWING APPOINTMENT HAS BEEN DELETED!" +
                                                                "\nAPPOINTMENT ID# : " + AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID()
                                                                +"\nAPPOINTMENT TYPE : "
                                                                + AppointmentTable.getSelectionModel().getSelectedItem().getType());
                                                        Optional<ButtonType> delete_result = delete_alert.showAndWait();
                                                        AppointmentsQuery.delete(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());
                                                }

                                        }

                        }catch(NullPointerException e){
                                SelectionError();
                        }

                }else{
                        SelectionError();
                }



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




        public static void SelectionError(){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SELECTION ERROR");
                alert.setContentText("No selection was made for this operation.");
                alert.showAndWait();

        }
}
