package cordinus.cordinus_global.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0
public class AddCustController {


    @FXML
    public void CustomerScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
}
