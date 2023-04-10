package cordinus.cordinus_global.controller;


import cordinus.cordinus_global.model.Customer;
import cordinus.cordinus_global.DAO.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

//https://www.youtube.com/watch?v=at4xyBOdgME
//https://www.youtube.com/watch?v=i0j2AmsJQz0


public class AddCustController implements CountryLoader {


    private ObservableList<Customer> customerdata;

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
    private ComboBox<String> CountriesComboBox;

    @FXML
    private ComboBox<String> StatesComboBox;

    private Customer customer;





    //https://www.youtube.com/watch?v=tw_NXq08NUE
    //https://www.youtube.com/watch?v=vpwvWdnILuo&list=PLmCsXDGbJHdia3cLNvK4e2Gp4S9TUkK3G&index=15
    //https://beginnersbook.com/2014/01/how-to-convert-12-hour-time-to-24-hour-date-in-java/



    private ObservableList<String> CountriesList = FXCollections.observableArrayList();
    private ObservableList<String> StatesList = FXCollections.observableArrayList();


    @Override
    public void initialize() throws SQLException {
        CountryLoader.LoadCountries (CountriesList);
        CountriesComboBox.setItems(CountriesList);
        StatesLoader.LoadStates(StatesList);
        StatesComboBox.setItems(StatesList);
    }



    @Override
    @FXML
    public void CreateCustomer(ActionEvent event) throws SQLException,IOException {


       //int custID = Integer.parseInt(Customer_ID.getText());//trying to display cust id
        String custname = Customer_Name.getText();
        String Address = AddressTxt.getText();
        String ZipCode = Postal_Code.getText();
        String PhoneNumber = Phone.getText();

        Date date = new Date();//use LocalDateTime
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

        Timestamp CreateDate = timestamp;
        Timestamp LastUpdate = timestamp;
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MainController.class.getResource("/cordinus/cordinus_global/LoginForm.fxml"));
//        loader.load();
//        LoginController loginController= loader.getController();
        //String CreatedBy =String.valueOf(loginController.UsernameTxt);
        String CreatedBy ="test";//toDo: update createby and last updated by to userlogin


        //String LastUpdatedBy = String.valueOf(loginController.UsernameTxt);
        String LastUpdatedBy ="test";
        int DivID = Integer.parseInt(Division_ID.getText());
        //System.out.print( CreateDate);

        String CountryName = CountriesComboBox.getValue();

        CustomersQuery.insert(custname, Address, ZipCode, PhoneNumber, CreateDate, CreatedBy, LastUpdate, LastUpdatedBy, DivID,CountryName);

        //timestamp for utc createddate and last update start as the same
        //createdby and lastupdate by user login, can be "test"/ the same
        //next steps are to implement user login, and to implement timestamps
        //modify sql statement, manually run sql statement on mysql workbench first, and embed in the code
        //e.g. (make sure order is correct:)
        ////Timestamp timestamp = UDT;
        //

//--there should be a combobox for division respective to the country
//--List of 3 countries to access from any controller
//


//
        //String sql = "SELECT COUNTRY FROM COUNTRIES";
//
//--get all divisions as objects, and do processing as list for each time you process the countries with forloop, see if division list matches country, and when match, add it to list
//
//if(divisionid = countryid){
//        DivisionComboBox.getItems.add(division);
//    }

        //https://stackoverflow.com/questions/57619189/how-to-query-data-into-jfxcombobox

    }








    @FXML
    public void CustomerScreenButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/cordinus/cordinus_global/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }



}
