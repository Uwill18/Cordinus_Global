package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.AppointmentsQuery;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.Alerts;
import cordinus.cordinus_global.model.Appointment;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    /**The initialize method gathers the keys from the resource bundle dictionary to translate upon system default settings*/
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        timeLabel.setText(rb.getString("Time"));
        CurrentTimeLbl.setText(ZoneId.systemDefault().toString());
        confirmButton.setText(rb.getString("Confirm"));
        exitButton.setText(rb.getString("Exit"));
    }



    /**Used the input text above to compare with a function called from User'sQuery
     * primary source was: https://www.youtube.com/watch?v=1jiuM-gNyBc
     * and wrote to the login_activity.txt according to:
     * https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=01cbcd0a-6a5b-4193-b914-ab490112a69b*/
    public void mainMenuScreenButton(ActionEvent event) throws SQLException, IOException {

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
            if (UsersQuery.userConfirmation(username,password)) {
                mainMenuView(event);
                outputFile.println( "ACCESS GRANTED to user of USERNAME: { " + username+ " } SIGN-IN TIME SHOWS AS: { "+ strDate+ " } " +ZoneId.systemDefault() + " Time.");
                System.out.println("File written!");
                /**upon successful validation the alert is called*/
                fifteenMinutesAlert();
                Alerts.nextApptUpdate();
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

    static void mainMenuView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Main"));
        stage.setScene(scene);
        stage.show();
    }


    public ObservableList fifteenMinutesAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(rb.getString("Upcoming"));
        alert.setTitle(rb.getString("AppointmentNotification"));
        alert.setContentText(checkFifteenMinutes());
        alert.showAndWait();
        return null;
    }

    /**This function checks all existing appointments for an appointment coming within fifteen minutes, and returns the result
     * for the above alert*/
    public String checkFifteenMinutes(){
        for(Appointment a: AppointmentsQuery.getAllAppointments()){//get list of appts
            if(a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(15))){
                LocalDateTime startformatTime = a.getStart();
                LocalDateTime endformatTime = a.getEnd();
                //DateTimeFormatter date_format = DateTimeFormatter.ofPattern(rb.getString("MM/dd/yyyy"));
                //String formatDate = ldt.format(date_format);
                DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
                String apptStart = startformatTime.format(time_format);
                String apptEnd = endformatTime.format(time_format);
                return rb.getString("You")+"\n"+
                        "\n"+rb.getString("APPOINTMENTID") +" "+ a.getAppointment_ID() +
                        "\n"+rb.getString("APPOINTMENT_TITLE") +" "+a.getTitle()+
                        "\n" + ZoneId.systemDefault() +" Time : " + apptStart + " - " + apptEnd;
            }
        }
        return rb.getString("There");
    }


    /**allows the User to exit the Loginform and application if needed*/
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}
