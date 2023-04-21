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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModCustController {
    //when edit an existing customer, you have to update lastupdate
    //by new login user
    //only change lastupdate and lastupdateby
    //**CreatedBy and CreateDate are NOT to be modified,
    //https://www.youtube.com/watch?v=1AmIKxHbLJo
    //https://www.youtube.com/watch?v=mY54KZkuZPQ


        @FXML
        private TextField AddressTxt;

        @FXML
        private TextField Customer_ID;

        @FXML
        private TextField Customer_Name;

        @FXML
        private TextField Division_ID;

        @FXML
        private TextField Phone;

        @FXML
        private TextField Postal_Code;

        @FXML
        private ComboBox<Country> CountriesComboBox;

        @FXML
        private ComboBox<Division> StatesComboBox;

        private Customer customer;

        private int index;


        private ObservableList<String> CountriesList = FXCollections.observableArrayList();


        public void initialize() throws SQLException {
               CountriesComboBox.setItems(CountriesQuery.getAllCountries());
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

        @FXML
        void ModifyCustomer(ActionEvent event) throws SQLException {
                int customerid = Integer.parseInt(Customer_ID.getText());
                String custname = Customer_Name.getText();
                String Address = AddressTxt.getText();
                String ZipCode = Postal_Code.getText();
                String PhoneNumber = Phone.getText();

                Date date = new Date();//use LocalDateTime
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = Timestamp.valueOf(formatter.format(date).toString());

                Timestamp CreateDate = timestamp;//stay same
                Timestamp LastUpdate = timestamp;//grab current
                String CreatedBy ="test";//stay same

                String LastUpdatedBy ="test";//grab curremt
                int DivID = Integer.parseInt(Division_ID.getText());
                CustomersQuery.update(custname, Address, ZipCode, PhoneNumber, CreateDate, CreatedBy, LastUpdate, LastUpdatedBy, DivID, customerid);

        }


        //ModifyCust must autopop selected data from customercontroller
        //allow editing of select fields, then pass that data back to the db
        //afterwards both the database and the app should hold an updated customers view



        public void OnActionSelectCountry(){
                //do a lamba connecting states to Divisions
                ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
                FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions, i-> i.getCountry_ID() == CountriesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
                StatesComboBox.setItems(selectedDivision);

        }


        //--there should be a combobox for division respective to the country
//--List of 3 countries to access from any controller
//
//    public static countriesdisplay(ArrayList<String> names) {
//        for (int i = 0; i < names.size(); i = i + 1) {
//            System.out.println(names.get(i));
//        }
//    }
//
//
//--get all divisions as objects, and do processing as list for each time you process the countries with forloop, see if division list matches country, and when match, add it to list
//
//if(divisionid = countryid){
//        DivisionComboBox.getItems.add(division);
//    }

        public void Customer_Passer(int index, Customer customer){
                this.customer = customer;
                this.index = index;

                Customer_ID.setText((String.valueOf(customer.getCustomer_ID())));//use same logic to setid for addapt and addcust
                Customer_Name.setText(customer.getCustomer_Name());
                AddressTxt.setText(customer.getAddress());
                Postal_Code.setText(customer.getPostal_Code());
                Phone.setText(customer.getPhone());
                Division_ID.setText((String.valueOf(customer.getDivision_ID())));

        }







}
