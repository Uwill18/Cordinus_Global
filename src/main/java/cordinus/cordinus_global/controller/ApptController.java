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
import java.util.ResourceBundle;
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

        public Appointment appointment;

        @FXML
        private Button addApptButton;

        @FXML
        private Label appointmentsTitle;

        @FXML
        private Button backButton;

        @FXML
        private Button deleteApptButton;

        @FXML
        private Button updateApptButton;


        public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

        public void initialize() throws SQLException, IOException {
                appointmentdata = FXCollections.observableArrayList();
                loadAppointments();
                setAppointmentCellTable();

                appointmentsTitle.setText(rb.getString("APPOINTMENTS"));
                AllRB.setText(rb.getString("All"));
                MonthRB.setText(rb.getString("Month"));
                WeekRB.setText(rb.getString("Week"));
                Appointment_ID.setText(rb.getString("ApptID"));
                Title.setText(rb.getString("Title"));
                Description.setText(rb.getString("Description"));
                Location.setText(rb.getString("Location"));
                Type.setText(rb.getString("Type"));
                Start.setText(rb.getString("Start"));
                End.setText(rb.getString("End"));
                Contact_ID.setText(rb.getString("Contact")+" "+rb.getString("ID"));
                Customer_ID.setText(rb.getString("Customer")+" "+rb.getString("ID"));
                User_ID.setText(rb.getString("User") + " " + rb.getString("ID"));

                addApptButton.setText(rb.getString("ADD"));
                updateApptButton.setText(rb.getString("UPDATE"));
                deleteApptButton.setText(rb.getString("DELETE"));
                backButton.setText(rb.getString("BACK"));
        }

        private void setAppointmentCellTable() {
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

        /**
         * To maximize refactoring, the sql queries that return the required object attributes for the observablelist are
         * executed in the relevant queries file, and the corresponding function that returns the appropriate observablelist
         * is stored in the observablelist in view.
         **E.g. appointment data is added to the CustomerTable in the view with <b>AppointmentTable.setItems</b>*/
        public void loadAppointments() {
                if (AllRB.isSelected()) {
                        appointmentdata = AppointmentsQuery.getAllAppointments();
                }
                if (MonthRB.isSelected()) {
                        appointmentdata = AppointmentsQuery.getMonthAppointments();
                }
                if (WeekRB.isSelected()) {
                        appointmentdata = AppointmentsQuery.getWeekAppointments();
                }
                AppointmentTable.setItems(appointmentdata);
        }


        @FXML
        public void onAddAppt(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AddAppt.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle(rb.getString("Add")+" "+rb.getString("Appointments"));
                stage.setScene(scene);
                stage.show();
        }

        @FXML
        public void onDeleteAppt(ActionEvent event) throws SQLException {
                //toDO if selected alert, else if nothing selected.. selection error
                if ((AppointmentTable.getSelectionModel().getSelectedItem() != null)) {
                        try {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle(rb.getString("DelWarn"));
                                alert.setHeaderText(rb.getString("DelAppt"));
                                alert.setContentText(rb.getString("DeleteApptQ")
                                        +  "\n"+rb.getString("APPOINTMENTID") +" "+ AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID()
                                        + "\n"+rb.getString("APPOINTMENT_TITLE") +" "+ AppointmentTable.getSelectionModel().getSelectedItem().getTitle());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() != ButtonType.CANCEL) {
                                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                                Alert delete_alert = new Alert(Alert.AlertType.CONFIRMATION);
                                                delete_alert.setTitle(rb.getString("DelConfirm"));
                                                delete_alert.setHeaderText(rb.getString("ApptDel"));
                                                delete_alert.setContentText(rb.getString("ApptDelResults") +
                                                        "\n"+rb.getString("APPOINTMENTID") + AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID()
                                                        + "\n"+rb.getString("APPOINTMENT_TITLE") + AppointmentTable.getSelectionModel().getSelectedItem().getTitle());
                                                Optional<ButtonType> delete_result = delete_alert.showAndWait();
                                                AppointmentsQuery.delete(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());
                                        }
                                }
                        } catch (NullPointerException e) {
                                Alerts.selectionError();
                        }
                } else {
                        Alerts.selectionError();
                }
        }

        @FXML
        public void onUpdateAppt(ActionEvent event) throws IOException {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/ModAppt.fxml"));
                        loader.load();
                        ModifyApptController modifyApptController = loader.getController();//get controller tied to view
                        modifyApptController.appt_Passer(AppointmentTable.getSelectionModel().getSelectedIndex(),
                                AppointmentTable.getSelectionModel().getSelectedItem());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle(rb.getString("ApptModification"));
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();
                } catch (NullPointerException e) {
                        Alerts.selectionError();
                }
        }

        @FXML
        public void mainMenuReturn(ActionEvent event) throws IOException {
                CustomerController.mainMenuReturnView(event);
        }

        /**
         * This radiobutton is responsible for Loading the different views according to the selection of each appointment
         * it is also used to refactor the appointmentdata.clear() method, which enables the view to be cleared every
         * time a new query is executed, so the new query results can be loaded to the view upon the selected condition
         */
        public void onRadioButton(ActionEvent event) throws SQLException {
                appointmentdata.clear();
                loadAppointments();
        }
}
