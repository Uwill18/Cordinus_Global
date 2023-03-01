package cordinus.cordinus_global.appointment;

import cordinus.cordinus_global.controller.MainController;
import cordinus.cordinus_global.customer.CustomerController;
import cordinus.cordinus_global.customer.CustomersList;
import cordinus.cordinus_global.helper.AppointmentsQuery;
import cordinus.cordinus_global.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        public void initialize() throws SQLException {
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
                        while(rs.next()){
                                /**This line filters the above sql string to select  data from specific columns, then send them to an instance of CustomersList
                                 * that appends to appointmentdata*/
                                appointmentdata.add(new AppointmentsList( rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(14), rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(12), rs.getString(13)));
                        }
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
        void OnDeleteAppt(ActionEvent event) {

        }

        @FXML
        void OnUpdateAppt(ActionEvent event) {

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
