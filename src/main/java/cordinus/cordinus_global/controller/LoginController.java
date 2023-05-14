package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.JDBC;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Appointment;
import cordinus.cordinus_global.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

//import static jdk.internal.org.jline.utils.Colors.s;

//https://stackoverflow.com/questions/39539838/javafx-populating-a-combobox-with-data-from-a-mysql-database-stringconverter-b

public class LoginController implements Initializable {

    public TextField UsernameTxt;
    public TextField PasswordTxt;
    public Label CurrentTimeLbl;
    public Button confirmButton;
    public Button exitButton;
    public Label timeLabel;
    public Label usernameLabel;
    public Label passwordLabel;

    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    /**The initialize method gathers the keys from the resource bundle dictionary to translate upon system default settings*/
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        timeLabel.setText(rb.getString("Time"));
        CurrentTimeLbl.setText(ZoneId.systemDefault().toString());
        confirmButton.setText(rb.getString("Confirm"));
        exitButton.setText(rb.getString("Exit"));

    }

    public void MainMenuScreenButton(ActionEvent event) throws SQLException, IOException {

        String username = UsernameTxt.getText();
        String password = PasswordTxt.getText();


        String filename ="login_activity.txt";

        //Create FileWriter object
        FileWriter fwriter = new FileWriter(filename, true);

        //Create and Open file
        PrintWriter outputFile = new PrintWriter(fwriter);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String strDate = formatter.format(date);



        try{
            /**Used the input text above to compare with a function called from User'sQuery
             * primary source was: https://www.youtube.com/watch?v=1jiuM-gNyBc */
            if (UsersQuery.userConfirmation(username,password)) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                outputFile.println( "ACCESS GRANTED to user of USERNAME: { " + username+ " } SIGN-IN TIME SHOWS AS: { "+ strDate+ " } " +ZoneId.systemDefault() + " Time.");
                System.out.println("File written!");
                /**upon successful validation the alert is called*/

                FifteenMinutesAlert();

            }else{
                outputFile.println( "ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate + " } " +ZoneId.systemDefault() + " Time.");
                Alerts.loginError();
            }
            outputFile.close();
        }catch(Exception e){
            outputFile.println("ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate + " } " +ZoneId.systemDefault() + " Time." );
            Alerts.loginError();
        }
    }


    public ObservableList FifteenMinutesAlert(){
/**This line filters the above sql string to select  data from specific columns, then sends them to an instance of Appointment
 * that appends to appointmentdata, and also used getTimestamp to pass to info back for appointment updates*/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Upcoming Appointment");
            alert.setTitle("Appointment Notification");
            alert.setContentText(CheckFifteenMinutes());
            alert.showAndWait();
        return null;
    }
    /**This function checks all existing appointments for an appointment coming within fifteen minutes, and returns the result
     * for the above alert*/

    public String CheckFifteenMinutes(){
        for(Appointment a: AppointmentsQuery.getAllAppointments()){//get list of appts
            if(a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(15))){
                    LocalDateTime ldt = a.getStart();
                    DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
                    String formatDate = ldt.format(date_format);
                    DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
                    String formatTime = ldt.format(time_format);
                    return  "You have an upcoming appointment with the following criteria:\n"+
                            "\nAppointment ID#: " + a.getAppointment_ID() +
                            "\nAppointment Date: " + formatDate +
                            "\nAppointment Start: " + formatTime +
                            "\nAppointment Description: " + a.getTitle();
            }
        }
        return "There are no upcoming appointments at this time.";
    }



    /**allows the User to exit the Loginform and application if needed*/
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}
