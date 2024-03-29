package cordinus.cordinus_global.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    /**This separate Homescreen leads to the Login screen, and does begin in French if that is the system default*/
    public Button proceedButton;
    public final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        proceedButton.setText(rb.getString("Proceed"));
    }

    @FXML
    void loginScreenButton(ActionEvent event) throws IOException {
        loginScreenView(event);
    }

    static void loginScreenView(ActionEvent event) throws IOException {
        MainController.loginScreenButtonView(event);
    }
}
