package cordinus.cordinus_global.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


//This file is responsible for loading data applicable to Main
//Password Validation
//and Navigation
public class MainController implements Initializable {



//    public Button proceedButton;
public Button proceedButton;
    @FXML
    private TextField PasswordTxt;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label CurrentTimeLbl;



//Password Validation//
    //https://www.youtube.com/watch?v=1jiuM-gNyBc  > used
    //https://www.youtube.com/watch?v=Y8F-k925O-w  good
    //https://www.youtube.com/watch?v=wII6ufsn82c
    //https://www.youtube.com/watch?v=ipz3Ezdeu3M
    //https://www.youtube.com/watch?v=1RftX8HMxdg >> preferred next step, best use case for multiple users,
    //                                               as it pulls info from the database

    //up results being correct or incorrect use file i/o options to print them to a file
    //in filewriter file, where i <  attempt sum (incorrect attempts, and correct attempts should sum less than 3)




    //Time Display according to time alerts videos

    //Exit button/sign out

//Main Menu Navigation//
    //ToLoginButton


    //create a dialog that has a picture with a timer to expire, and you proceed from there
    //toDO: have another controller, then main controller come in, may do welcome controller

    public void initialize(URL url, ResourceBundle resourceBundle){
        // proceedButton.setText(rb.getString("Proceed"));

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
    void LoginScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.show();
    }
        @FXML
        void CustomerScreenButton(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Customer");
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

}