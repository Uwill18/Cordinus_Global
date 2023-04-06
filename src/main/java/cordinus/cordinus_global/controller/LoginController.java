package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.appointment.ApptController;
import cordinus.cordinus_global.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField UsernameTxt;
    public TextField PasswordTxt;
    public Label CurrentTimeLbl;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        CurrentTimeLbl.setText(ZoneId.systemDefault().toString());
        //toDO: discuss the alert method
//        FXMLLoader loader = new FXMLLoader();
//        //loader.load();
//        ApptController apptController = loader.getController();
//        try {
//            apptController.FifteenMinutesAlert();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

    public void MainMenuScreenButton(ActionEvent event) throws SQLException, IOException {

        //toDo: remove when going back to validation
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
//        loader.load();
//        ApptController apptController = loader.getController();
//        apptController.FifteenMinutesAlert();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Main Menu");
//        stage.setScene(scene);
//        stage.show();


        String username = UsernameTxt.getText();
        String password = PasswordTxt.getText();

        /**compares values from the database with the text stored in the variables*/
          String sql = "SELECT * FROM USERS WHERE User_Name = '"+username+"' AND Password ='"+password+"'";
          PreparedStatement ps = JDBC.connection.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();

          String filename ="src/main/java/cordinus/cordinus_global/reports/login_activity.txt";


          //Create FileWriter object
          FileWriter fwriter = new FileWriter(filename, true);


        //Create and Open file
         PrintWriter outputFile = new PrintWriter(fwriter);

         //LocalDateTime UserLDT = LocalDateTime.now();
//        Date UserLDT = new Date();//use LocalDateTime
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        Timestamp timestamp = Timestamp.valueOf(formatter.format(UserLDT).toString());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String strDate = formatter.format(date);


        try{

                if (rs.next()) {
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


                }else{
                    outputFile.println( "ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate +" }.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Login Error");
                    alert.setContentText("Invalid Credentials were entered at this time. Please try again.");
                    alert.showAndWait();
                }
                outputFile.close();

        }catch(Exception e){

            outputFile.println("ACCESS DENIED to user of USERNAME: { " + username + " } ATTEMPTED SIGN-IN TIME SHOWS AS: { "+ strDate +" }." );
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("Invalid Credentials were entered at this time. Please try again.");
            alert.showAndWait();

          }

    }


    public String OnIntervalCheck() throws IOException{

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





    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}
