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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

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
        @FXML
        private Button modBtn;
        @FXML
        private Label addressLbl;
        @FXML
        private Button backBtn;
        @FXML
        private Label countryLbl;
        @FXML
        private Label customerIDLbl;
        @FXML
        private Label customerNameLbl;
        @FXML
        private Label divisionIDLbl;
        @FXML
        private Label phoneLbl;
        @FXML
        private Label postalCodeLbl;
        @FXML
        private Label stateLbl;

        private Customer customer;
        private int index;

        public static final ResourceBundle rb = ResourceBundle.getBundle("rb/Nat");

        public void initialize() throws SQLException {
               CountriesComboBox.setItems(CountriesQuery.getAllCountries());
               //StatesComboBox.setItems(DivisionsQuery.getAllDivisions());

                customerIDLbl.setText(rb.getString("CustomerID"));
                customerNameLbl.setText(rb.getString("CustomerName"));
                addressLbl.setText(rb.getString("Address"));
                postalCodeLbl.setText(rb.getString("Postal"));
                phoneLbl.setText(rb.getString("Phone"));
                countryLbl.setText(rb.getString("Country"));
                stateLbl.setText(rb.getString("State"));
                divisionIDLbl.setText("DivisionID");

                modBtn.setText(rb.getString("Modify"));
                backBtn.setText(rb.getString("Back"));
                //onActionSelectCountry();//see what happens

        }


        @FXML
        public void customerScreenButton(ActionEvent event) throws IOException {
                MainController.customerScreenView(event);
        }



        /**The CustomerPasser function  autopopulates selected data from customer controller
         and allows editing of select fields, to be passed back to the db.
         Afterwards both the database and the app hold an updated customers view based on the submissions
         from the ModifyCustomer Function*/

        @FXML
        public void modifyCustomer(ActionEvent event) throws SQLException, IOException {
                int customerid = Integer.parseInt(Customer_ID.getText());
                String custname = Customer_Name.getText();
                String Address = AddressTxt.getText();
                String ZipCode = Postal_Code.getText();
                String PhoneNumber = Phone.getText();

                Date date = new Date();//use LocalDateTime
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp LastUpdate = Timestamp.valueOf(formatter.format(date));//grab current

                String LastUpdatedBy = UsersQuery.getCurrentUserData().getUser_Name();//grab current
                int DivID = Integer.parseInt(Division_ID.getText());
                CustomersQuery.update(custname, Address, ZipCode, PhoneNumber, LastUpdate, LastUpdatedBy, DivID, customerid);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("custUpdated"));
                alert.setTitle((rb.getString("updatedCust1")));
                alert.setContentText(rb.getString("updatedCust2") +
                      rb.getString("CustomerName")+" : " +custname.toUpperCase() + "\n"+
                        rb.getString("CustomerID")+" : "+ customerid);
                alert.showAndWait();
                customerScreenButton(event);
        }


        /**This lambda expression filters the Observable list allDivisions of Type Division and matches
         * all division objects that share the same CountryID as the selected country from the CountriesComboBox.
         * This lambda expression is needed at this combox to filter the selection pool of divisions to their respective
         * countries by matching the CountryID attribute that both entities share.*/
        public void onActionSelectCountry(){
                try{
                        ObservableList<Division> allDivisions = DivisionsQuery.getAllDivisions();
                        FilteredList<Division> selectedDivision = new FilteredList<>(allDivisions, i-> i.getCountry_ID() == CountriesComboBox.getSelectionModel().getSelectedItem().getCountry_ID());
                        StatesComboBox.setItems(selectedDivision);

                }catch(NullPointerException e){
                        System.out.println("reconfiguring States");
                }
        }

        public void customer_Passer(int index, Customer customer){
                this.customer = customer;
                this.index = index;

                Customer_ID.setText((String.valueOf(customer.getCustomer_ID())));//use same logic to setid for addapt and addcust
                Customer_Name.setText(customer.getCustomer_Name());
                AddressTxt.setText(customer.getAddress());
                Postal_Code.setText(customer.getPostal_Code());
                Phone.setText(customer.getPhone());
                Division_ID.setText((String.valueOf(customer.getDivision_ID())));
                CountriesComboBox.setValue(CountriesQuery.getCountryByDivision(Integer.parseInt(customer.getDivision_ID())));
                StatesComboBox.setValue(DivisionsQuery.getDivisionByID(Integer.parseInt(customer.getDivision_ID())));
        }
        public void statesSelect(){
                try{
                        Division_ID.setText(String.valueOf(StatesComboBox.getValue().getDivision_ID()));
                }catch (NullPointerException e){
                        System.out.println("reconfiguring "+ e.getMessage());
                }

        }
}
