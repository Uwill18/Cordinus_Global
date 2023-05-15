package cordinus.cordinus_global.controller;


import cordinus.cordinus_global.DAO.CountriesQuery;
import cordinus.cordinus_global.DAO.DivisionsQuery;
import cordinus.cordinus_global.DAO.UsersQuery;
import cordinus.cordinus_global.model.Country;
import cordinus.cordinus_global.model.Customer;
import cordinus.cordinus_global.DAO.CustomersQuery;
import cordinus.cordinus_global.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

//import static javafx.scene.control.DatePicker.StyleableProperties.country;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0


public class AddCustController {
    @FXML
    private TextField AddressTxt;
    @FXML
    private Label AddressLbl;
    @FXML
    private Label CustomerID_Lbl;
    @FXML
    private Label CustomerNameLbl;
    @FXML
    private TextField Customer_ID;
    @FXML
    private TextField Customer_Name;
    @FXML
    private Label DivisionID_Lbl;
    @FXML
    private TextField Division_ID;
    @FXML
    private TextField Phone;
    @FXML
    private Label PhoneLbl;
    @FXML
    private Label PostalCodeLbl;
    @FXML
    private TextField Postal_Code;
    @FXML
    private ComboBox<Country> CountriesComboBox;
    @FXML
    private ComboBox<Division> StatesComboBox;

    //https://www.youtube.com/watch?v=tw_NXq08NUE
    //https://www.youtube.com/watch?v=vpwvWdnILuo&list=PLmCsXDGbJHdia3cLNvK4e2Gp4S9TUkK3G&index=15
    //https://beginnersbook.com/2014/01/how-to-convert-12-hour-time-to-24-hour-date-in-java/


    public void initialize() throws SQLException {
        CountriesComboBox.setItems(CountriesQuery.getAllCountries());
    }

    @FXML
    public void createCustomer(ActionEvent event) throws SQLException,IOException {
       //int custID = Integer.parseInt(Customer_ID.getText());//trying to display cust id
        String custname = Customer_Name.getText();
        String address = AddressTxt.getText();
        String zipCode = Postal_Code.getText();
        String phoneNumber = Phone.getText();
        String createdBy = UsersQuery.getCurrentUserData().getUser_Name();
        String lastUpdatedBy = UsersQuery.getCurrentUserData().getUser_Name();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());
        Timestamp createDate = timestamp;
        Timestamp lastUpdate = timestamp;


        /**Grabs divisionID from State selection, and populates in the field*/
        int divID = StatesComboBox.getValue().getDivision_ID();
        Division_ID.setText(String.valueOf(divID));

        CustomersQuery.insert(custname, address, zipCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divID);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("New Customer Added!");
        alert.setTitle("CUSTOMER ADDED");
        alert.setContentText( custname.toUpperCase() + " has been added to the list of customers!");
        alert.showAndWait();
        customerScreenButton(event);
    }


    public void onActionSelectCountry(){
        /**This lambda expression filters the Observable list allDivisions of Type Division and matches
         * all division objects that share the same CountryID as the selected country from the CountriesComboBox.
         * This lambda expression is needed at this combox to filter the selection pool of divisions to their respective
         * countries by matching the CountryID attribute that both entities share.*/
        ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
        FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions,i-> i.getCountry_ID() == CountriesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
        StatesComboBox.setItems(selectedDivision);
    }

    @FXML
    public void customerScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void statesSelect(){
        Division_ID.setText(String.valueOf(StatesComboBox.getValue().getDivision_ID()));
    }

}
