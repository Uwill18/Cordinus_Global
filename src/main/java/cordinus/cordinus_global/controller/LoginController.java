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


    public void initialize(URL url, ResourceBundle resourceBundle)  {
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        timeLabel.setText(rb.getString("Time"));
        CurrentTimeLbl.setText(ZoneId.systemDefault().toString());
        confirmButton.setText(rb.getString("Confirm"));
        exitButton.setText(rb.getString("Exit"));
        //FifteenMinutesAlert();

    }

    public void MainMenuScreenButton(ActionEvent event) throws SQLException, IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
//                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                        Scene scene = new Scene(fxmlLoader.load());
//                        stage.setTitle("Main Menu");
//                        stage.setScene(scene);
//                        stage.show();

        String username = UsernameTxt.getText();
        String password = PasswordTxt.getText();

        /**compares values from the database with the text stored in the variables*/
        String sql = "SELECT * FROM USERS WHERE User_Name = '"+username+"' AND Password ='"+password+"'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String filename ="login_activity.txt";

        //Create FileWriter object
        FileWriter fwriter = new FileWriter(filename, true);

        //Create and Open file
        PrintWriter outputFile = new PrintWriter(fwriter);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String strDate = formatter.format(date);



        try{
            if (UsersQuery.userConfirmation(username,password)) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                outputFile.println( "ACCESS GRANTED to user of USERNAME: { " + username+ " } SIGN-IN TIME SHOWS AS: { "+ strDate+" }.");
                //todo username and timestamp, then say if successful or not
                System.out.println("File written!");
                FifteenMinutesAlert();
                //User user = UsersQuery.setCurrentUserData(username,password);
                //System.out.println(user.getUser_Name() +" "+user.getPassword()+" "+user.getUser_ID());
                //User currentUser = new User(user.getUser_ID(), username,password);
               // System.out.println(currentUser.getUser_Name() +" "+currentUser.getPassword()+" "+currentUser.getUser_ID());
               // System.out.println(user.getUserData().getUser_ID() + " "+ user.getUserData().getUser_Name() + " " + user.getUserData().getPassword());
                //System.out.println(UsersQuery.setCurrentUserData(username,password).getUser_Name());

            }else{
                outputFile.println( "ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate +" }.");
                Alerts.loginError();
            }
            outputFile.close();
        }catch(Exception e){
            outputFile.println("ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate +" }." );
            Alerts.loginError();
        }
    }


    //toDo: clarify "e.  Write code to provide an alert when there is an appointment within 15 minutes of the user’s log-in. A custom message should be displayed in the user interface and include the appointment ID, date, and time.
    // If the user does not have any appointments within 15 minutes of logging in, display a custom message in the user interface indicating there are no upcoming appointments. x"

//
//
//    public String OnIntervalCheck() throws IOException{
//
//        LocalTime nextAppt = LocalTime.now().plusMinutes(15);
//
//        LocalTime currentTime = LocalTime.now();
//        int currenthour = LocalTime.now().getHour();//fetches the current hour to compare for logic
//        long timeDifference = ChronoUnit.MINUTES.between(currentTime,nextAppt);
//        long interval = timeDifference;
//
//
//
//
//        //toDo: see if you can make the app sign out after three suggested times e.g. 1 hour
//

    //toDo: for any user signing in the alert needs to check appointments within fifteen minutes of when the user signs in
    //toDo: where user is apart of the appointment

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
                DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String formatDate = ldt.format(dateformatter);
                DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
                String formatTime = ldt.format(timeformatter);
                return  "You have an upcoming appointment with the following criteria:\n"+
                        "\nAppointment Start: " + formatTime +
                        "\nAppointment Date: " + formatDate +
                        "\nAppointment ID#: " + a.getAppointment_ID();
            }
        }
        return "There are no upcoming appointments at this time.";
    }





//
//
//
//
//    }





    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}
