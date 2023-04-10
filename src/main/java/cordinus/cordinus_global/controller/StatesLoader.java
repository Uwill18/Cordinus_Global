package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatesLoader {
    static void LoadStates(ObservableList<String> StatesList) throws SQLException {


        String sql = "SELECT Division FROM First_Level_Divisions INNER JOIN Countries ON First_Level_Divisions.Country_ID = Countries.Country_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            StatesList.add(rs.getString("Division"));
        }
    }

    void initialize() throws SQLException;

    @FXML
    void CreateCustomer(ActionEvent event) throws SQLException, IOException;

    @FXML
    void CustomerScreenButton(ActionEvent event) throws IOException;
}
