package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.ContactsQuery;
import cordinus.cordinus_global.DAO.CustomersQuery;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.*;
import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.utils.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.parse;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0https://www.youtube.com/watch?v=i0j2AmsJQz0
//https://www.youtube.com/watch?v=3Ht-JMQh2JI
public class AddApptController implements Initializable {
    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");
    public ComboBox<LocalTime> StartTimeCombo;
    public ComboBox<LocalTime> EndTimeCombo;
    @FXML
    private DatePicker EndDatePicker;
    @FXML
    private DatePicker StartDatePicker;
    @FXML
    private ComboBox<Contact> ContactComboBox;
    @FXML
    private ComboBox<User> UserComboBox;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private TextField ApptIDTxt;
    @FXML
    private TextField ContactTxt;
    @FXML
    private TextField DescriptionTxt;
    @FXML
    private TextField LocationTxt;
    @FXML
    private TextField TitleTxt;
    @FXML
    private TextField CustomerIDTxt;
    @FXML
    private TextField UserIDTxt;
    @FXML
    private ComboBox<String> TypeComboBox;
    private Customer customer;
    private int index;
    private User user;

    /**Timing nested loop
     //Mr. Wabara reviewed timeloops with me to output to the combobox.
     //I learned that combining the times with the date picker to a timestamp
     //would help format the time, and this skill was transferrable as well to implementing
     the TypeComboBox as shown below, and the ContactComboBox.*/
    /**The Intervals are hard coded for appt start-times and appt-end-times*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int h = 0; h < 23; h++) {
            StartTimeCombo.getItems().add(LocalTime.of(h, 0));
            StartTimeCombo.getItems().add(LocalTime.of(h, 15));
            StartTimeCombo.getItems().add(LocalTime.of(h, 30));
            StartTimeCombo.getItems().add(LocalTime.of(h, 45));

            EndTimeCombo.getItems().add(LocalTime.of(h, 0));
            EndTimeCombo.getItems().add(LocalTime.of(h, 15));
            EndTimeCombo.getItems().add(LocalTime.of(h, 30));
            EndTimeCombo.getItems().add(LocalTime.of(h, 45));
        }
        String[] typeAppt = {"Upgrade", "Repair", "Consultation"};
        for (int i = 0; i < 3; i++) {
            TypeComboBox.getItems().add((i + 1) + " | " + typeAppt[i]);
        }
        ContactComboBox.setItems(ContactsQuery.getAllContacts());
        CustomerComboBox.setItems(CustomersQuery.getAllCustomers());
        UserComboBox.setItems(UsersQuery.getAllUsers());
        UserComboBox.setValue(UsersQuery.user);
        userUpdate();
    }

    /**
     * <p>Here my ComboBoxes <b>StartDatePicker</b> & <b>EndDatePicker</b>list local time objects
     * //then upon user selection, gets the value to be inserted for the times as a timestamp,
     * //making timeformatting as a sting not necessary in this case.
     * // The db takes said timestamp and converts it to sql server timezone
     * //easier than it would a string, especially if it is set to UTC</p>
     */

    /**Reviewed with Sunitha Kandalam,Juan Ruiz, Malcolm Wabara, and Carolyn Sher Decusatis the logic for verifying which appointments to insert.
     * Since this check is to verify IF something should be inserted, I thought a boolean
     * implementation similar to UsersQuery.userConfirmation(username,password) from my LoginController and the
     * CustomersQuery.deleteConfirmation(custID) in my  OnDeleteCustomer from the CustomerController would apply.
     * I learned to simplify, and to check when the appointmentOverlapCheck is not false to insert,
     * and otherwise alert if either of the boolean are false as below, as opposed to each time the functions
     * showed false. I also learned how to implement this in most reduced form so it could be reusable for the modification overlap,
     * and how to prevent overlaps for up to a year in booking.*/

    @FXML
    public void insertAppt(ActionEvent event) throws SQLException, IOException {

        int userid = Integer.parseInt(UserIDTxt.getText()); //get from combobox
        int custid = Integer.parseInt(CustomerIDTxt.getText()); // get from combobox
        String title = TitleTxt.getText();
        String description = DescriptionTxt.getText();
        String location = LocationTxt.getText();
        int contact = ContactComboBox.getValue().getContact_ID();
        String type = TypeComboBox.getValue();

        LocalDateTime date = LocalDateTime.now();//use LocalDateTime
        Timestamp timestamp = Timestamp.valueOf(date);

        Timestamp createDate = timestamp;
        Timestamp lastUpdate = timestamp;


        String createdBy = UsersQuery.getCurrentUserData().getUser_Name();
        String lastUpdatedBy = UsersQuery.getCurrentUserData().getUser_Name();


        ///Dates
        LocalDate startDate = LocalDate.from(StartDatePicker.getValue());
        LocalDate endDate = LocalDate.from(EndDatePicker.getValue());



        //Times
        LocalTime startTime = LocalTime.from(StartTimeCombo.getValue());
        LocalTime endTime = LocalTime.from(EndTimeCombo.getValue());

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        Timestamp startby = Timestamp.valueOf(start);
        Timestamp endby = Timestamp.valueOf(end);

        if (TimeUtil.businessHoursCheck(start, end) && (!TimeUtil.appointmentOverlapCheck(start, end, -1))) {//false
            AppointmentsQuery.insert(title, description, location, type, startby, endby, createDate, createdBy, lastUpdate, lastUpdatedBy, custid, userid, contact);

            DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
            String startformatDate = start.format(date_format);
            DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
            String startformatTime = start.format(time_format);
            String endformatTime = end.format(time_format);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("New Appointment Added!");
            alert.setTitle("APPOINTMENT ADDED");
            alert.setContentText("The appointment with the following criteria has been added : " +
                    "\nTitle : " + title +
                    "\nDate : " + startformatDate +
                    "\n" + ZoneId.systemDefault() + " Time : " + startformatTime + " - " + endformatTime);
            alert.showAndWait();

            System.out.println("The appointment with the following criteria has been added : " +
                    "\nTitle : " + title +
                    "\nDate : " + startformatDate +
                    "\n" + ZoneId.systemDefault() + " Time : " + startformatTime + " - " + endformatTime);
            apptScreenReturn(event);
        } else {
            Alerts.selectionWarning();
        }
    }


    @FXML
    public void apptScreenReturn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * At this time, it simply passes the id over to appointment screen
     * I had planned to pass additional data, but that maybe applied when I
     * revisit this project
     */

    public void customer_Passer(int index, Customer customer) {
        this.customer = customer;
        this.index = index;
        CustomerIDTxt.setText((String.valueOf(customer.getCustomer_ID())));
    }

    /**
     * upon selecting a contact, <b>contactUpdate</b> populates the contact id field with the correct value.
     * the below functions of <b>userUpdate</b> & <b>customerUpdate</b> are similar in nature and populate their
     * respective textfields with values selected from the comboboxes
     */
    public void contactUpdate() {
        ContactTxt.setText(String.valueOf(ContactComboBox.getValue().getContact_ID()));
    }

    public void userUpdate() {
        UserIDTxt.setText(String.valueOf(UserComboBox.getValue().getUser_ID()));
    }

    public void customerUpdate() {
        CustomerIDTxt.setText(String.valueOf(CustomerComboBox.getValue().getCustomer_ID()));
    }
}
