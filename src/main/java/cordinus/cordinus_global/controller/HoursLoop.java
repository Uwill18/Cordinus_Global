package cordinus.cordinus_global.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public interface HoursLoop {
    @FXML
    void UpdateAppt(ActionEvent event) throws SQLException;

    @FXML
    void ApptScreenReturn(ActionEvent event) throws IOException;

    void initialize(URL url, ResourceBundle resourceBundle);
}
