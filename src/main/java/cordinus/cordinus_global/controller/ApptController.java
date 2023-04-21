package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.model.Alerts;
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
        }

        public void LoadAppointments() throws SQLException {
                /**To maximize refactoring, the sql queries that return the required object attributes for the observablelist are
                 * executed in the relevant queries file, and the corresponding function that returns the appropriate observablelist
                 * is stored in the observablelist in view*/
                                        if (AllRB.isSelected()){
                                                appointmentdata = AppointmentsQuery.getAllAppointments();
                                        }
                                         if(MonthRB.isSelected()){
                                                appointmentdata = AppointmentsQuery.getMonthAppointments();
                                        }
                                        else if(WeekRB.isSelected()) {
                                               appointmentdata = AppointmentsQuery.getWeekAppointments();
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
                                Alerts.SelectionError();
                        }

                }else{
                        Alerts.SelectionError();
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
                        Alerts.SelectionError();
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



}
