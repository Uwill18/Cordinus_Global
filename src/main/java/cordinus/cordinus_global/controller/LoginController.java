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
import java.time.ZoneId;
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

         LocalDateTime UserLDT = LocalDateTime.now();


        try{

                if (rs.next()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                    outputFile.println( "ACCESS GRANTED to user of USERNAME: " + username+ " AT TIME: "+ UserLDT);
                    //todo username and timestamp, then say if successful or not
                    System.out.println("File written!");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
                    loader.load();
                    ApptController apptController = loader.getController();
                    apptController.FifteenMinutesAlert();

                }else{
                    outputFile.println( "ACCESS DENIED to user of USERNAME: " + username + " AT TIME: "+UserLDT);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Login Error");
                    alert.setContentText("Invalid Credentials were entered at this time. Please try again.");
                    alert.showAndWait();
                }
                outputFile.close();

        }catch(Exception e){

            outputFile.println( "ACCESS DENIED to user of USERNAME: " + username + " AT TIME: "+UserLDT);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Error");
            alert.setContentText("Invalid Credentials were entered at this time. Please try again.");
            alert.showAndWait();

          }










    }

    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}
