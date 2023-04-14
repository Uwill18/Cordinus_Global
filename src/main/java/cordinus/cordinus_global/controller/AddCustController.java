package cordinus.cordinus_global.controller;


import cordinus.cordinus_global.DAO.CountriesQuery;
import cordinus.cordinus_global.DAO.DivisionsQuery;
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
    private ComboBox<Country> CountriesComboBox;

    @FXML
    private ComboBox<Division> StatesComboBox;

    private Customer customer;





    //https://www.youtube.com/watch?v=tw_NXq08NUE
    //https://www.youtube.com/watch?v=vpwvWdnILuo&list=PLmCsXDGbJHdia3cLNvK4e2Gp4S9TUkK3G&index=15
    //https://beginnersbook.com/2014/01/how-to-convert-12-hour-time-to-24-hour-date-in-java/



    private ObservableList<String> CountriesList = FXCollections.observableArrayList();
    private ObservableList<String> StatesList = FXCollections.observableArrayList();


    @Override
    public void initialize() throws SQLException {
        //CountryLoader.LoadCountries (CountriesList);



        //ObservableList<Country> allCountries = CountriesQuery.getAllCountries();
        //ObservableList<String> countriesList = FXCollections.observableArrayList();
        //CountriesList.add(allCountries.toString());
        // CountriesComboBox.setItems(countriesList);



                //ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
                //Division division = new Division(1,"USA",1);
                //int x = division.getDivision_ID();
        //System.out.println(allDivisions);


                //String countryNames = CountriesQuery.getCountryByDivision(1).getCountry();

//                ObservableList<String> countryNamesList = FXCollections.observableArrayList();
//                countryNamesList.add(countryNames);
//                System.out.println(countryNamesList);

//                List<String> countryNames = new LinkedList<>();
////                countryNames.add(CountriesQuery.getCountryByDivision(1).getCountry());
////                countryNames.add(CountriesQuery.getCountryByDivision(2).getCountry());
////                countryNames.add(CountriesQuery.getCountryByDivision(3).getCountry());
//
//                Iterator<String> countryNamesIterator = countryNames.iterator();
//
//                while(countryNamesIterator.hasNext()){
//                    System.out.println(countryNamesIterator.next().toString());
//                }

//                for(String s : countryNames){
//                    System.out.println(s);
//                }

        CountriesComboBox.setItems(CountriesQuery.getAllCountries());

        // ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
//        FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions,i-> i.getCountry_ID() == StatesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
//        StatesComboBox.setItems(selectedDivision);

















        //System.out.println(allCountries);
        //String CountriesList = CountriesQuery.getCountryByDivision(1).getCountry().toString();


        //System.out.println(allDivisions);









        //int divid = division.getDivision_ID();

        //a string observablelist is needed to set the combobox items




       //System.out.println(CountriesList);




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
        String CreatedBy ="test";//toDo: update createby and last updated by to userlogin


        String LastUpdatedBy ="test";

        /**Grabs divisionID from State selection, and populates in the field*/
        int DivID = StatesComboBox.getValue().getDivision_ID();
        Division_ID.setText(String.valueOf(DivID));


        CustomersQuery.insert(custname, Address, ZipCode, PhoneNumber, CreateDate, CreatedBy, LastUpdate, LastUpdatedBy, DivID);



        //get country by division needs an int parameter, maybe from divisions






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



    public void OnActionSelectCountry(){
        //do a lamba connecting states to Divisions
        ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
        FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions,i-> i.getCountry_ID() == CountriesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
        StatesComboBox.setItems(selectedDivision);

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
