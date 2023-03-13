package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.SimpleTimeZone;


//This file is responsible for loading data applicable to Main
//Password Validation
//and Navigation
public class MainController {


    @FXML
    private TextField PasswordTxt;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label CurrentTimeLbl;


//Updated Main Menu Button Navigation


//Password Validation//
    //https://www.youtube.com/watch?v=1jiuM-gNyBc  > used
    //https://www.youtube.com/watch?v=Y8F-k925O-w  good
    //https://www.youtube.com/watch?v=wII6ufsn82c
    //https://www.youtube.com/watch?v=ipz3Ezdeu3M
    //https://www.youtube.com/watch?v=1RftX8HMxdg >> preferred next step, best use case for multiple users,
    //                                               as it pulls info from the database

    //up results being correct or incorrect use file i/o options to print them to a file
    //in filewriter file, where i <  attempt sum (incorrect attempts, and correct attempts should sum less than 3)




    //for now use next button

    //Language Translation set by radio button, and connected to Language bundle
    //if translate is selected may have to set up screens just for french translation

    //Time Display according to time alerts videos

    //Exit button/sign out

//Main Menu Navigation//
    //ToLoginButton




@FXML
void LoginScreenButton(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));
    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Login Form");

    stage.setScene(scene);

    stage.show();


}
    //MainMenuScreenButton

    public void TimeLoad(){
        LocalTime logintime = LocalTime.now();

        System.out.println(logintime);
        CurrentTimeLbl.setText(logintime.toString());
    }



    @FXML
    void MainMenuScreenButton(ActionEvent event) throws IOException, SQLException {

        String username = UsernameTxt.getText();
        String password = PasswordTxt.getText();

        /**compares values from the database with the text stored in the variables*/
        String sql = "SELECT * FROM USERS WHERE User_Name = '"+username+"' AND Password ='"+password+"'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        String filename ="src/main/java/cordinus/cordinus_global/reports/ValidationLog.txt";


        //Create FileWriter object
        FileWriter fwriter = new FileWriter(filename, true);


        //Create and Open file
        PrintWriter outputFile = new PrintWriter(fwriter);

        for(int numAttempt=0; numAttempt < 3; numAttempt++){
            if (rs.next()){
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                outputFile.println((numAttempt+1)+" Valid Access" );
                System.out.println("File written!");
            }else {
                System.out.println("no");
                outputFile.println((numAttempt+1)+"Access Denied" );

            }
            outputFile.close();

        }









//        if((username.equals("test") && password.equals("test"))||(username.equals("admin") && password.equals("admin"))){
//            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(fxmlLoader.load());
//            stage.setTitle("Main Menu");
//            stage.setScene(scene);
//            stage.show();
//            System.out.println("Login verified");//replace with filewriting logic
//        }else{
//            System.out.println("Access denied");//replace with filewriting logic
//        }




    }




    //AppointmentScreenButton

    @FXML
    void AppointmentScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }


    //CustomerScreenButton

        @FXML
        void CustomerScreenButton(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }
    //ReportScreenButton

    @FXML
    void ReportScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/ReportScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
    //LoginReturnButton




    //exit
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }


    //exit

//Exit//


}