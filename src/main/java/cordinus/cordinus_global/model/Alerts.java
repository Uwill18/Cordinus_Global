package cordinus.cordinus_global.model;

import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class Alerts {

    /**For best practices in reusable code I implemented an Alerts class for most alerts so I could reuse them as needed**/
    public static final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");


    public static void selectionWarning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(rb.getString("OVERLAP"));
        alert.setContentText(rb.getString("Current"));
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
        alert.setTitle(rb.getString("Error"));
        alert.setHeaderText(rb.getString("SELECTION"));
        alert.setContentText(rb.getString("No"));
        alert.showAndWait();
    }

    public static void deleteError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rb.getString("Error"));
        alert.setHeaderText(rb.getString("DELETION"));
        alert.setContentText(rb.getString("Existing"));
        alert.showAndWait();
    }

    public static void valueWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(rb.getString("VALUE"));
        alert.setContentText(rb.getString("valwarn"));
        alert.showAndWait();
    }

    //Upcoming = Upcoming Appointment
    //AppointmentNotification = Appointment Notification
    //remStatus2

    public static void nextApptUpdate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(rb.getString("AppointmentNotification"));
        //alert.setTitle(rb.getString("addAppt"));
        //alert.setHeaderText(rb.getString("Upcoming"));
        alert.setHeaderText(rb.getString("apptRemTitle"));
        alert.setContentText(rb.getString("There")+"\n"+rb.getString("remStatus2"));
        alert.showAndWait();
    }
}
