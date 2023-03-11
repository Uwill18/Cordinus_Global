package cordinus.cordinus_global.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


//This file is responsible for loading data applicable to Main
//Password Validation
//and Navigation
public class MainController {


    @FXML
    private TextField PasswordTxt;

    @FXML
    private TextField UsernameTxt;


//Updated Main Menu Button Navigation


//Password Validation//
    //https://www.youtube.com/watch?v=1jiuM-gNyBc
    //https://www.youtube.com/watch?v=Y8F-k925O-w  good
    //https://www.youtube.com/watch?v=wII6ufsn82c

    //up results being correct or incorrect use file i/o options to print them to a file




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



    @FXML
    void MainMenuScreenButton(ActionEvent event) throws IOException {

        String username = UsernameTxt.getText();
        String password = PasswordTxt.getText();

        if((username.equals("test") && password.equals("test"))||(username.equals("admin") && password.equals("admin"))){
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
            System.out.println("Login verified");
        }else{
            System.out.println("Access denied");
        }




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