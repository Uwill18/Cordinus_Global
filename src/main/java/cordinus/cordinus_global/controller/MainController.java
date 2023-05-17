package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.model.User;
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
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button backButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;

    public static final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

    public void initialize(URL url, ResourceBundle resourceBundle){

        appointmentsButton.setText(rb.getString("Appointments"));
        customersButton.setText(rb.getString("Customers"));
        reportsButton.setText(rb.getString("Reports"));
        backButton.setText(rb.getString("Back"));


    }

    @FXML
    void appointmentScreenButton(ActionEvent event) throws IOException {
        appointmentScreenButtonView(event);
    }

    static void appointmentScreenButtonView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Appointments"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loginScreenButton(ActionEvent event) throws IOException {
        loginScreenButtonView(event);
    }

    static void loginScreenButtonView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("LoginForm"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void customerScreenButton(ActionEvent event) throws IOException {
        customerScreenButtonView(event);
    }

    static void customerScreenButtonView(ActionEvent event) throws IOException {
        customerScreenView(event);
    }

    static void customerScreenView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Customers"));
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void reportScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/ReportScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Reports"));
        stage.setScene(scene);
        stage.show();
    }

}