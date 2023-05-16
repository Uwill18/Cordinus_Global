package cordinus.cordinus_global.model;

import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class Alerts {

    /**For best practices in reusable code I implemented an Alerts class for most alerts so I could reuse them as needed**/
    public static final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    public static void selectionWarning(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("OVERLAP WARNING");
        alert.setContentText("Current selection either conflicts with the established business hours or overlaps existing appointments. Please Try Again.");
        alert.showAndWait();

    }

    public static void valueWarning(){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("VALUE WARNING");
        alert.setContentText("The values entered render invalid. Please Try Again.");
        alert.showAndWait();

    }

    public static void loginError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rb.getString("Error"));
        alert.setHeaderText(rb.getString("Login"));
        alert.setContentText(rb.getString("Invalid"));
        alert.showAndWait();
    }


    public static void selectionError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("SELECTION ERROR");
        alert.setContentText("No selection was made for this operation.");
        alert.showAndWait();
    }

    public static void deleteError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("DELETION ERROR");
        alert.setContentText("Existing customer appointments must be deleted prior to deleting this customer.");
        alert.showAndWait();
    }
}
