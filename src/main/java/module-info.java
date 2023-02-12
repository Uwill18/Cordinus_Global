module cordinus.cordinus_global {
    requires javafx.controls;
    requires javafx.fxml;


    opens cordinus.cordinus_global to javafx.fxml;
    exports cordinus.cordinus_global;
}