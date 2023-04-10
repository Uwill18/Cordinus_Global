package cordinus.cordinus_global.controller;

import cordinus.cordinus_global.DAO.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface CountryLoader {
    static void LoadCountries(ObservableList<String> CountriesList) throws SQLException {


        String sql = "SELECT Country FROM Countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CountriesList.add(rs.getString("Country"));
        }
    }

    void initialize() throws SQLException;

    @FXML
    void CreateCustomer(ActionEvent event) throws SQLException, IOException;

    @FXML
    void CustomerScreenButton(ActionEvent event) throws IOException;
}
