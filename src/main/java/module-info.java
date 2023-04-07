module cordinus.cordinus_global {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens cordinus.cordinus_global to javafx.fxml;
    exports cordinus.cordinus_global;
    exports cordinus.cordinus_global.model;
    opens cordinus.cordinus_global.model to javafx.fxml;
    exports cordinus.cordinus_global.reports;
    opens cordinus.cordinus_global.reports to javafx.fxml;
    exports cordinus.cordinus_global.controller;
    opens cordinus.cordinus_global.controller to javafx.fxml;

}