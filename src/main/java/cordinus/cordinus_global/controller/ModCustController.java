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

        /**The CustomerPasser function  autopopulates selected data from customer controller
         and allows editing of select fields, to be passed back to the db.
        Afterwards both the database and the app hold an updated customers view based on the submissions
         from the ModifyCustomer Function*/

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

                Timestamp CreateDate = timestamp;//remove
                Timestamp LastUpdate = timestamp;//grab current
                String CreatedBy ="test";//remove

                String LastUpdatedBy ="test";//grab current
                int DivID = Integer.parseInt(Division_ID.getText());
                CustomersQuery.update(custname, Address, ZipCode, PhoneNumber, CreateDate, CreatedBy, LastUpdate, LastUpdatedBy, DivID, customerid);
        }


        public void OnActionSelectCountry(){
                /**This lambda expression filters the Observable list allDivisions of Type Division and matches
                 * all division objects that share the same CountryID as the selected country from the CountriesComboBox.
                 * This lambda expression is needed at this combox to filter the selection pool of divisions to their respective
                 * countries by matching the CountryID attribute that both entities share.*/
                ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
                FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions, i-> i.getCountry_ID() == CountriesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
                StatesComboBox.setItems(selectedDivision);
        }

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
